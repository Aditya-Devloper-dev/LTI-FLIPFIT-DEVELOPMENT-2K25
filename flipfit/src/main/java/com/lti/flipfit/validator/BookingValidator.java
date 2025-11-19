package com.lti.flipfit.validator;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.exceptions.FlipFitBookingExceptions.InvalidBookingException;

public class BookingValidator {

    public static void validateBookingRequest(Booking booking) {

        if (booking.getCustomerId() == null || booking.getCustomerId().isBlank()) {
            throw new InvalidBookingException("Customer ID is required");
        }

        if (booking.getCenterId() == null || booking.getCenterId().isBlank()) {
            throw new InvalidBookingException("Center ID is required");
        }

        if (booking.getSlotId() == null || booking.getSlotId().isBlank()) {
            throw new InvalidBookingException("Slot ID is required");
        }
    }
}
