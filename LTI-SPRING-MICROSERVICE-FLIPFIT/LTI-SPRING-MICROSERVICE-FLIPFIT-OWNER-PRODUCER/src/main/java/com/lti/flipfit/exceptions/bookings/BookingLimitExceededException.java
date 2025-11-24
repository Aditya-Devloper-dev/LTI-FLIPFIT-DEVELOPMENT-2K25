package com.lti.flipfit.exceptions.bookings;

/**
 * Author :
 * Version : 1.0
 * Description : Thrown when a user tries to exceed the allowed number of
 * bookings.
 */
public class BookingLimitExceededException extends RuntimeException {

    private final String errorCode = "BOOKING_LIMIT_EXCEEDED";

    public BookingLimitExceededException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
