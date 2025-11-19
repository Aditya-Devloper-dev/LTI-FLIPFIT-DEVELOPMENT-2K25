package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Author      :
 * Version     : 1.0
 * Description : Base class for all custom exceptions in FlipFit. Holds common fields
 *               such as errorCode and HTTP status to standardize exception handling.
 */
public abstract class BaseException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public BaseException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
