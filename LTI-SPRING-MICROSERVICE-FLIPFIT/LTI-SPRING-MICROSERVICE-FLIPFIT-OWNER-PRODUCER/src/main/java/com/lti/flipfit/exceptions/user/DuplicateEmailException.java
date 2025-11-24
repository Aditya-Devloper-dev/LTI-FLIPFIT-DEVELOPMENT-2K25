package com.lti.flipfit.exceptions.user;

/**
 * Author :
 * Version : 1.0
 * Description : Exception thrown when email is duplicate.
 */

public class DuplicateEmailException extends RuntimeException {

    private final String errorCode = "DUPLICATE_EMAIL";

    public DuplicateEmailException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
