package com.lti.flipfit.exceptions;

public class InvalidInputException extends RuntimeException {

    private final String errorCode = "INVALID_INPUT";

    public InvalidInputException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
