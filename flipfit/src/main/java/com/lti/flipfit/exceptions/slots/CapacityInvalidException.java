package com.lti.flipfit.exceptions.slots;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when a slot's capacity is zero, negative, or otherwise invalid.
 */
public class CapacityInvalidException extends RuntimeException {

    private final String errorCode = "INVALID_CAPACITY";

    public CapacityInvalidException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
