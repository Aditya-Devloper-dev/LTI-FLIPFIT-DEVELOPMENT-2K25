package com.lti.flipfit.exceptions;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when input is invalid.
 */

public class InvalidInputException extends RuntimeException {

    private final String errorCode = "INVALID_INPUT";

    public InvalidInputException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
