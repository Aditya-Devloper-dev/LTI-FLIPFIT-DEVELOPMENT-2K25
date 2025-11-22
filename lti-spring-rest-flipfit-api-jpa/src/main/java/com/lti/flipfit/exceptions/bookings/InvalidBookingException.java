package com.lti.flipfit.exceptions.bookings;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when booking is invalid.
 */

public class InvalidBookingException extends RuntimeException {

    public InvalidBookingException(String message) {
        super(message);
    }
}
