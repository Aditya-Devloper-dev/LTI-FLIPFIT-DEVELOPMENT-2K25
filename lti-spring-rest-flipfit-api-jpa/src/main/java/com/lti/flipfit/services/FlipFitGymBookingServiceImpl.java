package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.bookings.*;
import com.lti.flipfit.exceptions.slots.SlotFullException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymBookingService interface.
 */

@Service
public class FlipFitGymBookingServiceImpl implements FlipFitGymBookingService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymBookingServiceImpl.class);

    private final FlipFitGymBookingRepository bookingRepo;
    private final FlipFitGymCustomerRepository customerRepo;
    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymCenterRepository centerRepo;

    public FlipFitGymBookingServiceImpl(FlipFitGymBookingRepository bookingRepo,
            FlipFitGymCustomerRepository customerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymCenterRepository centerRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.slotRepo = slotRepo;
        this.centerRepo = centerRepo;
    }

    /*
     * @Method: bookSlot
     * 
     * @Description: Books a new slot after validating duplicate bookings and
     * booking limits.
     * 
     * @MethodParameters: booking -> Booking request payload
     * 
     * @Exception: Throws BookingAlreadyExistsException,
     * BookingLimitExceededException
     */
    @Override
    public String bookSlot(GymBooking booking) {

        // Extract IDs from incoming JSON
        Long customerId = Long.valueOf(booking.getCustomer().getCustomerId());
        Long slotId = booking.getSlot().getSlotId();
        Long centerId = booking.getCenter().getCenterId();

        logger.info("Attempting to book slot {} at center {} for customer {}", slotId, centerId, customerId);

        GymCustomer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new InvalidBookingException("Invalid customerId"));

        GymSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new InvalidBookingException("Invalid slotId"));

        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new InvalidBookingException("Invalid centerId"));

        // Check duplicate booking
        if (bookingRepo.existsByCustomerAndSlot(customer, slot)) {
            throw new BookingAlreadyExistsException("User already booked this slot");
        }

        // Check availability
        if (slot.getAvailableSeats() <= 0) {
            throw new SlotFullException("Slot is full");
        }

        // Reduce slot availability
        slot.setAvailableSeats(slot.getAvailableSeats() - 1);
        slotRepo.save(slot);

        // Now set actual persistent objects
        booking.setCustomer(customer);
        booking.setSlot(slot);
        booking.setCenter(center);
        booking.setStatus("BOOKED");
        booking.setCreatedAt(LocalDateTime.now());

        GymBooking saved = bookingRepo.save(booking);

        logger.info("Booking successful. Booking ID: {}", saved.getBookingId());

        return "Booking successful with ID: " + saved.getBookingId();
    }

    /*
     * @Method: cancelBooking
     * 
     * @Description: Cancels an existing booking after validating booking existence
     * and state.
     * 
     * @MethodParameters: bookingId -> Unique booking identifier
     * 
     * @Exception: Throws BookingNotFoundException,
     * BookingCancellationNotAllowedException
     */
    @Override
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

        bookingRepo.delete(booking);

        logger.info("Booking cancelled successfully. Booking ID: {}", bookingId);

        return "Booking cancelled with ID: " + bookingId;
    }

}
