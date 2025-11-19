package com.lti.flipfit.validator;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.exceptions.InvalidInputException;

public class BookingValidator {

    public static void validateBookingRequest(Booking booking) {

        if (booking.getCustomerId() == null || booking.getCustomerId().isBlank()) {
            throw new InvalidInputException("Customer ID is required");
        }

        if (booking.getCenterId() == null || booking.getCenterId().isBlank()) {
            throw new InvalidInputException("Center ID is required");
        }

        if (booking.getSlotId() == null || booking.getSlotId().isBlank()) {
            throw new InvalidInputException("Slot ID is required");
        }
    }
}
