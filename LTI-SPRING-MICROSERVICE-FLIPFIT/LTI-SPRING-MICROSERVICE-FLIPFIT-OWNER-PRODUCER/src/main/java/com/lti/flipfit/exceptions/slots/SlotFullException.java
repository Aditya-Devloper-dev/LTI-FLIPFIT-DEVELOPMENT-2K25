package com.lti.flipfit.exceptions.slots;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when slot is full.
 */

public class SlotFullException extends RuntimeException {

    private final String errorCode = "SLOT_FULL";

    public SlotFullException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
