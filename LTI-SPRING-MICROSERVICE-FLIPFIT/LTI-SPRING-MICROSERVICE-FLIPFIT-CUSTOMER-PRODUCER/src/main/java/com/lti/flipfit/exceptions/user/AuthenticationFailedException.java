package com.lti.flipfit.exceptions.user;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when authentication failed.
 */

public class AuthenticationFailedException extends RuntimeException {

    private final String errorCode = "AUTHENTICATION_FAILED";

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
