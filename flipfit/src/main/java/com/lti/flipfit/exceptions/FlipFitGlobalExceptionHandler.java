package com.lti.flipfit.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Author      :
 * Version     : 1.0
 * Description : Centralized exception handler for FlipFit application. Converts all
 *               custom exceptions into standardized JSON responses. Also handles
 *               unexpected exceptions gracefully.
 */
@ControllerAdvice
public class FlipFitGlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {

        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode()
        );

        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(response,
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
