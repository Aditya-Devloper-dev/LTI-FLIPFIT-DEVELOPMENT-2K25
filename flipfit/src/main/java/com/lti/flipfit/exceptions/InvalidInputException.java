package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when the request contains missing, invalid, or improperly
 *               formatted input fields. Typically mapped to 400 BAD REQUEST.
 */
public class InvalidInputException extends BaseException {

    public InvalidInputException(String message) {
        super(message, "INVALID_INPUT", HttpStatus.BAD_REQUEST);
    }
}
