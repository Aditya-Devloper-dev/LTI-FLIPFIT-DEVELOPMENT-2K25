package com.lti.flipfit.exceptions.bookings;

public class BookingNotFoundException extends RuntimeException {

    private final String errorCode = "BOOKING_NOT_FOUND";

    public BookingNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
