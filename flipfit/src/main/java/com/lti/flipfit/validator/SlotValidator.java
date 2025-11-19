package com.lti.flipfit.validator;

import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.exceptions.InvalidInputException;

public class SlotValidator {

    public static void validateSlot(Slot slot) {

        if (slot.getStartTime() == null) {
            throw new InvalidInputException("Slot start time is required");
        }

        if (slot.getEndTime() == null) {
            throw new InvalidInputException("Slot end time is required");
        }

        if (slot.getCapacity() <= 0) {
            throw new InvalidInputException("Slot capacity must be greater than zero");
        }
    }
}
