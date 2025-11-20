package com.lti.flipfit.exceptions.user;

public class AuthenticationFailedException extends RuntimeException {

    private final String errorCode = "AUTHENTICATION_FAILED";

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
