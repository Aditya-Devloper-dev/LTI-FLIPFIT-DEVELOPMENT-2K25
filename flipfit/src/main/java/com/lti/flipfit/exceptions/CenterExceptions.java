package com.lti.flipfit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Author      :
 * Version     : 1.0
 * Description : Contains all custom exceptions related to Admin operations such as
 *               center creation, slot creation, time validation, and capacity rules.
 */
public class CenterExceptions {

    /*
     * @Exception: CenterAlreadyExistsException
     * @Description: Thrown when a center with the same name and city already exists.
     * @ErrorCode: CENTER_ALREADY_EXISTS
     * @HttpStatus: 409 CONFLICT
     */
    public static class CenterAlreadyExistsException extends BaseException {
        public CenterAlreadyExistsException(String message) {
            super(message, "CENTER_ALREADY_EXISTS", HttpStatus.CONFLICT);
        }
    }

    /*
     * @Exception: CenterNotFoundException
     * @Description: Thrown when the requested centerId does not exist in the system.
     * @ErrorCode: CENTER_NOT_FOUND
     * @HttpStatus: 404 NOT FOUND
     */
    public static class CenterNotFoundException extends BaseException {
        public CenterNotFoundException(String message) {
            super(message, "CENTER_NOT_FOUND", HttpStatus.NOT_FOUND);
        }
    }
}
