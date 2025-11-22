package com.lti.flipfit.validator;

/**
 * Author :
 * Version : 1.0
 * Description : Validator class for Center operations.
 */

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.exceptions.InvalidInputException;

public class CenterValidator {

    public static void validateCreateCenter(GymCenter center) {

        if (center.getCenterName() == null || center.getCenterName().isBlank()) {
            throw new InvalidInputException("Center name is required");
        }

        if (center.getCity() == null || center.getCity().isBlank()) {
            throw new InvalidInputException("City is required");
        }

        if (center.getContactNumber() == null || center.getContactNumber().isBlank()) {
            throw new InvalidInputException("Contact number is required");
        }
    }
}
