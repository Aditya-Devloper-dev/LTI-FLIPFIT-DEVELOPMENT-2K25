package com.lti.flipfit.exceptions;

public class CenterNotFoundException extends RuntimeException {

    private final String errorCode = "CENTER_NOT_FOUND";

    public CenterNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
