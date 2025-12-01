package com.lti.flipfit.validator;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.UnauthorizedAccessException;
import org.springframework.stereotype.Component;

@Component
public class OwnerValidator {

    public void validateToggleCenter(GymCenter center, Long ownerId) {
        if (!center.getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this center");
        }
    }

    public void validateToggleSlot(GymSlot slot, Long ownerId) {
        if (!slot.getCenter().getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this slot");
        }
    }

    public void validateAddCenter(GymOwner owner) {
        if (!owner.isApproved()) {
            throw new UnauthorizedAccessException("Owner approval is pending from Admin Side");
        }
    }

    public void validateUpdateCenter(GymCenter center, Long ownerId) {
        if (!center.getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this center");
        }
    }

    public void validateAddSlot(GymSlot slot, GymCenter center, Long ownerId) {
        if (slot.getStartTime().isAfter(slot.getEndTime()) || slot.getStartTime().equals(slot.getEndTime())) {
            throw new InvalidInputException("Start time must be before end time");
        }

        if (!center.getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this center");
        }

        if (!center.getIsActive()) {
            throw new UnauthorizedAccessException("Center is not active yet");
        }
    }
}
