package com.lti.flipfit.exceptions;

import java.time.LocalDateTime;

/**
 * Author      :
 * Version     : 1.0
 * Description : DTO returned for all error responses. Ensures consistent structure
 *               containing message, errorCode, timestamp, and success flag.
 */
public class ErrorResponse {

    private boolean success;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, String errorCode) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
