package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when a user attempts to book a slot that has no available seats.
 *               Typically mapped to 409 CONFLICT.
 */
public class SlotsNotAvailableException extends BaseException {

    public SlotsNotAvailableException(String message) {
        super(message, "SLOTS_NOT_AVAILABLE", HttpStatus.CONFLICT);
    }
}
