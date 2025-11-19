package com.lti.flipfit.validator;

import com.lti.flipfit.beans.GymCenter;
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
