package com.lti.flipfit.exceptions.bookings;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when booking is not found.
 */

public class BookingNotFoundException extends RuntimeException {

    private final String errorCode = "BOOKING_NOT_FOUND";

    public BookingNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
