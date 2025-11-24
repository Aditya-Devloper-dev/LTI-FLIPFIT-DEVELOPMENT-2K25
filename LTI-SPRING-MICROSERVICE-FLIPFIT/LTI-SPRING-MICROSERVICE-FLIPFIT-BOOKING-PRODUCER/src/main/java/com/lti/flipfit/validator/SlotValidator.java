package com.lti.flipfit.validator;

/**
 * Author :
 * Version : 1.0
 * Description : Validator class for Slot operations.
 */

import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;

public class SlotValidator {

    public static void validateSlot(GymSlot gymSlot) {

        if (gymSlot.getStartTime() == null) {
            throw new InvalidInputException("Slot start time is required");
        }

        if (gymSlot.getEndTime() == null) {
            throw new InvalidInputException("Slot end time is required");
        }

        if (gymSlot.getCapacity() <= 0) {
            throw new InvalidInputException("Slot capacity must be greater than zero");
        }
    }
}
