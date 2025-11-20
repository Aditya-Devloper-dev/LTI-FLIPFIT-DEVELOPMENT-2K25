package com.lti.flipfit.exceptions.center;

/**
 * Thrown when a gym center with same name/location already exists.
 */
public class CenterAlreadyExistsException extends RuntimeException {

    private final String errorCode = "CENTER_ALREADY_EXISTS";

    public CenterAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
