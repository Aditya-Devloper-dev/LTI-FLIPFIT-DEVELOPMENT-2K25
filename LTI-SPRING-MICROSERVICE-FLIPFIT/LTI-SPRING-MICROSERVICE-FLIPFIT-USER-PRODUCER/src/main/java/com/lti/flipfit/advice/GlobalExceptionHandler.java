package com.lti.flipfit.advice;

import com.lti.flipfit.entity.ErrorResponse;
import com.lti.flipfit.exceptions.user.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Author :
 * Version : 1.0
 * Description : Global exception handler for FlipFit application. Handles all
 * custom exceptions and returns structured error responses.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildResponse(Exception ex, String errorCode, WebRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                errorCode,
                request.getDescription(false));
    }

    // ------------------------- USER EXCEPTIONS -------------------------

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex,
            WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false));
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "INVALID_INPUT", request);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    // ------------------------- FALLBACK EXCEPTION -------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "INTERNAL_SERVER_ERROR");

        return ResponseEntity.status(500).body(error);
    }

}
