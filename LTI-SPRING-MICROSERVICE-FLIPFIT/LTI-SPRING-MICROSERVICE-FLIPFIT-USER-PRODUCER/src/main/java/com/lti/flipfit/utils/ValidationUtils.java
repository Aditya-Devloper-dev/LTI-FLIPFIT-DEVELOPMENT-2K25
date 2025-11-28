package com.lti.flipfit.utils;

import com.lti.flipfit.exceptions.InvalidInputException;

/**
 * Author :
 * Version : 1.0
 * Description : Utility class for common validation logic.
 */
public class ValidationUtils {

    /**
     * Validates the email format.
     * 
     * @param email The email to validate.
     * @throws InvalidInputException if the email is null, empty, or invalid format.
     */
    public static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Email is required");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("Invalid email format");
        }
    }
}
