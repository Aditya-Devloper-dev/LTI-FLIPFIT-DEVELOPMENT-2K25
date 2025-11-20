package com.lti.flipfit.exceptions.slots;

public class SlotAlreadyExistsException extends RuntimeException {

    private final String errorCode = "SLOT_ALREADY_EXISTS";

    public SlotAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
