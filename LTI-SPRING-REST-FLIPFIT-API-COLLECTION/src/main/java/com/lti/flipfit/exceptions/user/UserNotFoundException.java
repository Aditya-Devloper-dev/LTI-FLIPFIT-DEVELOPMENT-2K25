package com.lti.flipfit.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    private final String errorCode = "USER_NOT_FOUND";

    public UserNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
