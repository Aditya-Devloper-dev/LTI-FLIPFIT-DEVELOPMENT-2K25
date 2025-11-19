package com.lti.flipfit.exceptions;

/**
 * Author      :
 * Version     : 1.0
 * Description : Enum that stores all FlipFit-specific error codes to ensure consistent
 *               error responses across controllers and services.
 */
public enum ErrorCode {

    USER_NOT_FOUND,
    REGISTRATION_NOT_DONE,
    SLOTS_NOT_AVAILABLE,
    APPROVAL_NOT_DONE,
    DETAILS_NOT_AVAILABLE,
    BOOKING_CONFLICT,
    CENTER_NOT_FOUND,
    SLOT_NOT_FOUND,
    PAYMENT_FAILED,
    UNAUTHORIZED_ACTION,
    INVALID_INPUT
}
