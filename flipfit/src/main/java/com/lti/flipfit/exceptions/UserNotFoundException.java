package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Author      :
 * Version     : 1.0
 * Description : Thrown when a requested user does not exist in the system.
 *               Typically mapped to 404 NOT FOUND.
 */
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, "USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
