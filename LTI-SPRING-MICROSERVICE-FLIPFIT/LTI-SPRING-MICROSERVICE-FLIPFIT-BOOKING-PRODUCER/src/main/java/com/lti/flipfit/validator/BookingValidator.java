package com.lti.flipfit.validator;

import com.lti.flipfit.dao.FlipFitGymBookingDAO;
import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.bookings.BookingAlreadyExistsException;
import com.lti.flipfit.exceptions.bookings.InvalidBookingException;
import com.lti.flipfit.exceptions.slots.SlotFullException;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BookingValidator {

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;
    @Autowired
    private FlipFitGymSlotRepository slotRepo;
    @Autowired
    private FlipFitGymCenterRepository centerRepo;
    @Autowired
    private FlipFitGymBookingDAO bookingDAO;

    public void validateBooking(GymBooking booking) {
        Long customerId = Long.valueOf(booking.getCustomer().getCustomerId());
        Long slotId = booking.getSlot().getSlotId();
        Long centerId = booking.getCenter().getCenterId();

        // 1. Validate Entities Existence
        GymCustomer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new InvalidBookingException("Invalid customerId"));
        GymSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new InvalidBookingException("Invalid slotId"));
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new InvalidBookingException("Invalid centerId"));

        // 2. Validate Slot belongs to Center
        if (!slot.getCenter().getCenterId().equals(centerId)) {
            throw new InvalidBookingException("Slot " + slotId + " does not belong to center " + centerId);
        }

        // 3. Check Duplicate Booking
        if (bookingDAO.checkDuplicateBooking(customerId, slotId)) {
            throw new BookingAlreadyExistsException("User already booked this slot");
        }

        // 4. Check Availability
        if (slot.getAvailableSeats() <= 0) {
            throw new SlotFullException("Slot is full");
        }

        // 5. Validate Date and Time
        LocalDate bookingDate = booking.getBookingDate();
        if (bookingDate != null) {
            if (bookingDate.isBefore(LocalDate.now())) {
                throw new InvalidBookingException("Cannot book a slot for a past date.");
            }
            if (bookingDate.equals(LocalDate.now()) && slot.getStartTime().isBefore(java.time.LocalTime.now())) {
                throw new InvalidBookingException("Cannot book a slot for a past time.");
            }
        }

        // 6. Validate Price
        if (slot.getPrice() == null || slot.getPrice() <= 0) {
            throw new InvalidBookingException("Slot price is not set or invalid.");
        }

        // Set fetched entities back to booking to ensure they are managed/complete
        booking.setCustomer(customer);
        booking.setSlot(slot);
        booking.setCenter(center);
    }
}
