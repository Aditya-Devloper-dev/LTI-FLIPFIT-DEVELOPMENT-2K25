package com.lti.flipfit.exceptions.slots;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when slot time is invalid.
 */

public class InvalidSlotTimeException extends RuntimeException {

    private final String errorCode = "INVALID_SLOT_TIME";

    public InvalidSlotTimeException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
