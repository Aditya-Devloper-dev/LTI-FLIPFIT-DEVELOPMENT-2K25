package com.lti.flipfit.exceptions.bookings;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when a booking cannot be cancelled due to policy or time restrictions.
 */
public class BookingCancellationNotAllowedException extends RuntimeException {

    private final String errorCode = "BOOKING_CANCELLATION_NOT_ALLOWED";

    public BookingCancellationNotAllowedException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
