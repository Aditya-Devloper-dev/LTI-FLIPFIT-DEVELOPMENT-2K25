package com.lti.flipfit.validator;

/**
 * Author :
 * Version : 1.0
 * Description : Validator class for Booking operations.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.exceptions.InvalidInputException;

public class BookingValidator {

    public static void validateBookingRequest(GymBooking booking) {

        if (booking.getCustomer() == null) {
            throw new InvalidInputException("Customer ID is required");
        }

        if (booking.getCenter() == null) {
            throw new InvalidInputException("Center ID is required");
        }

        if (booking.getSlot() == null) {
            throw new InvalidInputException("Slot ID is required");
        }
    }
}
