package com.lti.flipfit.exceptions.user;

public class UserAlreadyExistsException extends RuntimeException {

    private final String errorCode = "USER_ALREADY_EXISTS";

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
