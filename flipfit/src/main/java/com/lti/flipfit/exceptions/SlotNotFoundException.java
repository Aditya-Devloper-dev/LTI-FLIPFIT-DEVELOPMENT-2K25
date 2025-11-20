package com.lti.flipfit.exceptions;

public class SlotNotFoundException extends RuntimeException {

    private final String errorCode = "SLOT_NOT_FOUND";

    public SlotNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
