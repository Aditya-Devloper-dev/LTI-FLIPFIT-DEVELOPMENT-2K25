package com.lti.flipfit.exceptions.center;

/**
 * Thrown when the center location (city/area) is invalid or does not meet standards.
 */
public class InvalidCenterLocationException extends RuntimeException {

    private final String errorCode = "INVALID_CENTER_LOCATION";

    public InvalidCenterLocationException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
