package com.lti.flipfit.exceptions.slots;

public class SlotFullException extends RuntimeException {

    private final String errorCode = "SLOT_FULL";

    public SlotFullException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
