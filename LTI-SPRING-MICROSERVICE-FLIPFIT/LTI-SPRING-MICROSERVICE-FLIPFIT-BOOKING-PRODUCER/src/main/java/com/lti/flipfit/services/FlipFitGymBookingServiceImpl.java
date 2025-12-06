package com.lti.flipfit.services;

import com.lti.flipfit.client.PaymentClient;
import com.lti.flipfit.dao.FlipFitGymBookingDAO;
import com.lti.flipfit.entity.*;
import com.lti.flipfit.enums.BookingStatus;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.bookings.*;
import com.lti.flipfit.repository.*;
import com.lti.flipfit.validator.BookingValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private NotificationProducer notificationProducer;
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
    public String bookSlot(GymBooking booking) {

        // Extract IDs from incoming JSON
        Long customerId = Long.valueOf(booking.getCustomer().getCustomerId());
        Long slotId = booking.getSlot().getSlotId();
        Long centerId = booking.getCenter().getCenterId();

        logger.info("Attempting to book slot {} at center {} for customer {}", slotId, centerId, customerId);

        // Set booking date if missing (defaults to today)
        if (booking.getBookingDate() == null) {
            booking.setBookingDate(LocalDate.now());
        }

        // Perform all validations
        bookingValidator.validateBooking(booking);

        // Entities are set in the validator
        GymSlot slot = booking.getSlot();

        // Step 1: Initialize Booking with PENDING status
        // We do NOT decrement the seat yet.
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

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

            savedBooking.setStatus(BookingStatus.BOOKED);
            bookingRepo.save(savedBooking);

            logger.info("Booking confirmed. Seat deducted. Booking ID: {}", savedBooking.getBookingId());

            // Step 4: Send Notification
            try {
                GymCustomer customer = customerRepo.findById(customerId).orElse(null);
                if (customer != null && customer.getUser() != null) {
                    String email = customer.getUser().getEmail();
                    String message = "Your booking for " + slot.getActivity() + " at " + slot.getStartTime() + " at "
                            + booking.getCenter().getCenterName()
                            + " is confirmed. Booking ID: " + savedBooking.getBookingId();
                    String subject = "Booking Confirmation - FlipFit";
                    Long userId = customer.getUser().getUserId();
                    com.lti.flipfit.dto.NotificationEvent event = new com.lti.flipfit.dto.NotificationEvent(userId,
                            email,
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
    public String cancelBooking(Long bookingId) {
        logger.info("Attempting to cancel booking with ID: {}", bookingId);

        GymBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (BookingStatus.CANCELLED == booking.getStatus()) {
            throw new BookingCancellationNotAllowedException("Booking is already cancelled");
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

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);

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

    @Scheduled(fixedRate = 3600000) // Run every hour
    @Transactional
    public void markBookingsAsAttended() {
        logger.info("Running scheduled task to mark bookings as ATTENDED");
        java.util.List<GymBooking> bookings = bookingRepo.findByStatus(BookingStatus.BOOKED);
        LocalDateTime now = LocalDateTime.now();

        for (GymBooking booking : bookings) {
            if (booking.getBookingDate() != null && booking.getSlot() != null) {
                LocalDateTime slotEndTime = LocalDateTime.of(booking.getBookingDate(), booking.getSlot().getEndTime());
                if (slotEndTime.plusMinutes(20).isBefore(now)) {
                    booking.setStatus(BookingStatus.ATTENDED);
                    bookingRepo.save(booking);
                    logger.info("Marked Booking ID {} as ATTENDED", booking.getBookingId());
                }
            }
        }
    }
}
