package com.lti.flipfit.exceptions.bookings;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when the user attempts to book a slot they already booked.
 */
public class BookingAlreadyExistsException extends RuntimeException {

    private final String errorCode = "BOOKING_ALREADY_EXISTS";

    public BookingAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
