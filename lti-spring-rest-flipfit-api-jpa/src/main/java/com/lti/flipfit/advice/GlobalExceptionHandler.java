package com.lti.flipfit.advice;

import com.lti.flipfit.entity.ErrorResponse;
import com.lti.flipfit.exceptions.*;
import com.lti.flipfit.exceptions.bookings.*;
import com.lti.flipfit.exceptions.center.*;
import com.lti.flipfit.exceptions.slots.*;
import com.lti.flipfit.exceptions.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Author      :
 * Version     : 1.0
 * Description : Global exception handler for FlipFit application. Handles all
 *               custom exceptions and returns structured error responses.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildResponse(Exception ex, String errorCode, WebRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                errorCode,
                request.getDescription(false)
        );
    }

    // ------------------------- USER EXCEPTIONS -------------------------

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationFailed(AuthenticationFailedException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "INVALID_INPUT", request);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    // ------------------------- CENTER EXCEPTIONS -------------------------

    @ExceptionHandler(CenterAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCenterAlreadyExists(CenterAlreadyExistsException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CenterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCenterNotFound(CenterNotFoundException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CenterUpdateNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleCenterUpdateNotAllowed(CenterUpdateNotAllowedException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCenterLocationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCenterLocation(InvalidCenterLocationException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }


    // ------------------------- SLOT EXCEPTIONS -------------------------

    @ExceptionHandler(SlotNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSlotNotFound(SlotNotFoundException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "SLOT_NOT_FOUND", request);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SlotAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSlotExists(SlotAlreadyExistsException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "SLOT_ALREADY_EXISTS", request);
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SlotFullException.class)
    public ResponseEntity<ErrorResponse> handleSlotFull(SlotFullException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "SLOT_FULL", request);
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidSlotTimeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSlotTime(InvalidSlotTimeException ex, WebRequest request) {
        ErrorResponse res = buildResponse(ex, "INVALID_SLOT_TIME", request);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CapacityInvalidException.class)
    public ResponseEntity<ErrorResponse> handleCapacityInvalid(CapacityInvalidException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }


    // ------------------------- BOOKING EXCEPTIONS -------------------------

    @ExceptionHandler(BookingAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookingAlreadyExists(BookingAlreadyExistsException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookingCancellationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleBookingCancellationNotAllowed(BookingCancellationNotAllowedException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BookingLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleBookingLimitExceeded(BookingLimitExceededException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.TOO_MANY_REQUESTS); // 429
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookingNotFound(BookingNotFoundException ex, WebRequest request) {
        ErrorResponse res = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                ex.getErrorCode(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookingException(InvalidBookingException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setMessage(ex.getMessage());
        error.setErrorCode("INVALID_BOOKING_ERROR");
        error.setDetails(null);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



    // ------------------------- FALLBACK EXCEPTION -------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "INTERNAL_SERVER_ERROR"
        );

        return ResponseEntity.status(500).body(error);
    }


}
