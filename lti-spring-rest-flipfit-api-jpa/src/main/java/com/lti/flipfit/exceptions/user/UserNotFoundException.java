package com.lti.flipfit.exceptions.user;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when user is not found.
 */

public class UserNotFoundException extends RuntimeException {

    private final String errorCode = "USER_NOT_FOUND";

    public UserNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
