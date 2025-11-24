package com.lti.flipfit.exceptions.slots;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when slot is not found.
 */

public class SlotNotFoundException extends RuntimeException {

    private final String errorCode = "SLOT_NOT_FOUND";

    public SlotNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
