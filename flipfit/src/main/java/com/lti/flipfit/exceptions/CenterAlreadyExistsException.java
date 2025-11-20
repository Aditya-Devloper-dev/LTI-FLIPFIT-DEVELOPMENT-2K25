package com.lti.flipfit.exceptions;

public class CenterAlreadyExistsException extends RuntimeException {

    private final String errorCode = "CENTER_ALREADY_EXISTS";

    public CenterAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
