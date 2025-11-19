package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

public class BookingExceptions {

    public static class SlotNotFoundException extends BaseException {
        public SlotNotFoundException(String msg) {
            super(msg, "SLOT_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
    }

    public static class BookingNotFoundException extends BaseException {
        public BookingNotFoundException(String msg) {
            super(msg, "BOOKING_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
    }

    public static class SlotFullException extends BaseException {
        public SlotFullException(String msg) {
            super(msg, "SLOT_FULL", HttpStatus.CONFLICT);
        }
    }

    public static class BookingConflictException extends BaseException {
        public BookingConflictException(String msg) {
            super(msg, "BOOKING_CONFLICT", HttpStatus.CONFLICT);
        }
    }

    public static class InvalidBookingException extends BaseException {
        public InvalidBookingException(String msg) {
            super(msg, "INVALID_BOOKING", HttpStatus.BAD_REQUEST);
        }
    }
}
