package com.lti.flipfit.exceptions.center;

/**
 * Thrown when a centerId does not exist in the system.
 */
public class CenterNotFoundException extends RuntimeException {

    private final String errorCode = "CENTER_NOT_FOUND";

    public CenterNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
