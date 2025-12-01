package com.lti.flipfit.services;

import com.lti.flipfit.client.PaymentClient;
import com.lti.flipfit.dao.FlipFitGymBookingDAO;
import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.bookings.*;
import com.lti.flipfit.repository.*;
import com.lti.flipfit.validator.BookingValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymBookingService interface.
 */

@Service
public class FlipFitGymBookingServiceImpl implements FlipFitGymBookingService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymBookingServiceImpl.class);

    @Autowired
    private FlipFitGymBookingRepository bookingRepo;
    @Autowired
    private FlipFitGymSlotRepository slotRepo;
    @Autowired
    private FlipFitGymPaymentRepository paymentRepo;
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private FlipFitGymBookingDAO bookingDAO;
    @Autowired
    private BookingValidator bookingValidator;
    @Autowired
    private com.lti.flipfit.service.NotificationProducer notificationProducer;
    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

    /**
     * @methodname - bookSlot
     * @description - Books a new slot after validating duplicate bookings and
     *              booking limits.
     * @param - booking The booking request payload.
     * @return - A success message with the booking ID.
     */
    @Override
    @CacheEvict(value = "payments", allEntries = true)
    public String bookSlot(GymBooking booking) {

        // Extract IDs from incoming JSON
        Long customerId = Long.valueOf(booking.getCustomer().getCustomerId());
        Long slotId = booking.getSlot().getSlotId();
        Long centerId = booking.getCenter().getCenterId();

        logger.info("Attempting to book slot {} at center {} for customer {}", slotId, centerId, customerId);

        // Perform all validations
        bookingValidator.validateBooking(booking);

        // Entities are set in the validator
        GymSlot slot = booking.getSlot();

        // Step 1: Initialize Booking with PENDING status
        // We do NOT decrement the seat yet.
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());
        if (booking.getBookingDate() == null) {
            booking.setBookingDate(LocalDate.now());
        }

        GymBooking savedBooking = bookingRepo.save(booking);
        logger.info("Booking initialized with PENDING status. Booking ID: {}", savedBooking.getBookingId());

        // Step 2: Process Payment via Feign Client
        try {
            // Assuming default payment mode "CARD"
            String paymentMode = "CARD";

            ResponseEntity<String> response = paymentClient.processPayment(
                    String.valueOf(customerId),
                    slot.getPrice(),
                    paymentMode,
                    savedBooking.getBookingId());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Payment failed with status: " + response.getStatusCode());
            }

            logger.info("Payment successful for Booking ID: {}", savedBooking.getBookingId());

            // Step 3: On Payment Success - Confirm Booking and Decrement Seat
            slot.setAvailableSeats(slot.getAvailableSeats() - 1);
            slotRepo.save(slot);

            savedBooking.setStatus("BOOKED");
            bookingRepo.save(savedBooking);

            logger.info("Booking confirmed. Seat deducted. Booking ID: {}", savedBooking.getBookingId());

            // Step 4: Send Notification
            try {
                GymCustomer customer = customerRepo.findById(customerId).orElse(null);
                if (customer != null && customer.getUser() != null) {
                    String email = customer.getUser().getEmail();
                    String message = "Your booking for slot " + slotId + " at center " + centerId
                            + " is confirmed. Booking ID: " + savedBooking.getBookingId();
                    String subject = "Booking Confirmation - FlipFit";
                    com.lti.flipfit.dto.NotificationEvent event = new com.lti.flipfit.dto.NotificationEvent(email,
                            message, subject);
                    notificationProducer.sendNotification(event);
                }
            } catch (Exception e) {
                logger.error("Failed to send notification for Booking ID: {}", savedBooking.getBookingId(), e);
                // Don't fail the booking if notification fails
            }

            return "Booking successful with ID: " + savedBooking.getBookingId();

        } catch (Exception e) {
            // Step 4: On Payment Failure - Rollback
            logger.error("Payment failed for Booking ID: {}. Rolling back.", savedBooking.getBookingId(), e);

            // Delete the pending booking so it doesn't clutter the DB
            bookingRepo.delete(savedBooking);

            throw new InvalidBookingException("Payment failed: " + e.getMessage());
        }
    }

    /**
     * @methodname - cancelBooking
     * @description - Cancels an existing booking after validating booking existence
     *              and state.
     * @param - bookingId The unique booking identifier.
     * @return - A success message with the cancelled booking ID.
     */
    @Override
    @Transactional
    @CacheEvict(value = "payments", allEntries = true)
    public String cancelBooking(Long bookingId) {
        logger.info("Attempting to cancel booking with ID: {}", bookingId);

        GymBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if ("COMPLETED".equalsIgnoreCase(booking.getStatus())) {
            throw new BookingCancellationNotAllowedException("Cannot cancel completed bookings");
        }

        // Restore seat
        GymSlot slot = booking.getSlot();
        slot.setAvailableSeats(slot.getAvailableSeats() + 1);
        slotRepo.save(slot);

        // Check cancellation policy (1 hour before slot)
        LocalDate bookingDate = booking.getBookingDate();
        if (bookingDate == null) {
            bookingDate = booking.getCreatedAt().toLocalDate();
        }

        LocalDateTime slotDateTime = LocalDateTime.of(bookingDate, slot.getStartTime());
        long hoursDifference = ChronoUnit.HOURS.between(LocalDateTime.now(), slotDateTime);

        if (hoursDifference >= 1) {
            // Trigger Refund
            try {
                paymentClient.refundPayment(bookingId);
                logger.info("Refund initiated for Booking ID: {}", bookingId);
            } catch (Exception e) {
                logger.error("Refund failed for Booking ID: {}", bookingId, e);
            }
        } else {
            logger.info("Cancellation is less than 1 hour before slot. No refund.");
        }

        bookingRepo.delete(booking);

        logger.info("Booking cancelled successfully. Booking ID: {}", bookingId);

        return "Booking cancelled with ID: " + bookingId;
    }

    /**
     * @methodname - viewPayments
     * @description - Retrieves payments based on filter type.
     * @param - filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param - date The specific date for DAILY filter (YYYY-MM-DD).
     * @return - List of GymPayment objects.
     */
    @Override
    @Cacheable(value = "payments", key = "{#filterType, #date}")
    public java.util.List<com.lti.flipfit.entity.GymPayment> viewPayments(String filterType, String date) {
        if ("ALL".equalsIgnoreCase(filterType)) {
            return paymentRepo.findAll();
        }

        LocalDate refDate;
        try {
            if (date != null && !date.isBlank()) {
                refDate = LocalDate.parse(date);
            } else {
                refDate = LocalDate.now();
            }
        } catch (Exception e) {
            throw new InvalidInputException("Invalid date format. Expected YYYY-MM-DD");
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime = refDate.atTime(23, 59, 59);

        switch (filterType.toUpperCase()) {
            case "DAILY":
                startDateTime = refDate.atStartOfDay();
                break;
            case "WEEKLY":
                startDateTime = refDate.minusDays(6).atStartOfDay();
                break;
            case "MONTHLY":
                startDateTime = refDate.minusMonths(1).atStartOfDay();
                break;
            default:
                throw new InvalidInputException("Invalid filter type. Allowed: ALL, DAILY, WEEKLY, MONTHLY");
        }

        logger.info("Fetching payments from {} to {}", startDateTime, endDateTime);
        return bookingDAO.findPaymentsByDateRange(startDateTime, endDateTime);
    }
}
