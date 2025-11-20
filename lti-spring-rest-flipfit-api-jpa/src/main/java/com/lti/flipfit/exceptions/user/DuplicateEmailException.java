package com.lti.flipfit.exceptions.user;

public class DuplicateEmailException extends RuntimeException {

    private final String errorCode = "DUPLICATE_EMAIL";

    public DuplicateEmailException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
