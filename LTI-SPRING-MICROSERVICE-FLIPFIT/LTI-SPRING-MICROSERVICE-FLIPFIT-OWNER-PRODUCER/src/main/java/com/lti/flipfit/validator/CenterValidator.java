package com.lti.flipfit.validator;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.center.CenterUpdateNotAllowedException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Author :
 * Version : 1.0
 * Description : Validator class for Center operations.
 */
@Component
public class CenterValidator {

    public void validateCreateCenter(GymCenter center) {

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

    public void validateGetSlotsByDate(String date, GymCenter center) {
        if (date == null || date.isBlank()) {
            throw new InvalidInputException("Date cannot be empty");
        }

        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid date format. Expected yyyy-MM-dd");
        }

        if (!Boolean.TRUE.equals(center.getIsActive())) {
            throw new CenterUpdateNotAllowedException("Center is inactive");
        }
    }
}
