package com.lti.flipfit.exceptions.center;

/**
 * Thrown when a center field that cannot be updated is attempted to be modified.
 */
public class CenterUpdateNotAllowedException extends RuntimeException {

    private final String errorCode = "CENTER_UPDATE_NOT_ALLOWED";

    public CenterUpdateNotAllowedException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
