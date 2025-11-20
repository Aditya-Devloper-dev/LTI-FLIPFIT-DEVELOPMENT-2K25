package com.lti.flipfit.validator;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.exceptions.InvalidInputException;

public class BookingValidator {

    public static void validateBookingRequest(GymBooking booking) {

        if (booking.getCustomerId() == null ) {
            throw new InvalidInputException("Customer ID is required");
        }

        if (booking.getCenterId() == null ) {
            throw new InvalidInputException("Center ID is required");
        }

        if (booking.getSlotId() == null ) {
            throw new InvalidInputException("Slot ID is required");
        }
    }
}
