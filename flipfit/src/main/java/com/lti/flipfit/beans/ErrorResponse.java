package com.lti.flipfit.beans;

import java.time.LocalDateTime;

/**
 * Author      :
 * Version     : 1.0
 * Description : DTO returned for all error responses. Ensures consistent structure
 *               containing message, errorCode, timestamp, and success flag.
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private String details;

    public ErrorResponse() { }

    public ErrorResponse(LocalDateTime timestamp, String message, String errorCode, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public ErrorResponse(LocalDateTime timestamp, String message, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorCode = errorCode;
        this.details = null;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetails() {
        return details;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
