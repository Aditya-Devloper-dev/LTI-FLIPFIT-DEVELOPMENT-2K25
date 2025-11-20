package com.lti.flipfit.exceptions.slots;

public class InvalidSlotTimeException extends RuntimeException {

    private final String errorCode = "INVALID_SLOT_TIME";

    public InvalidSlotTimeException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
