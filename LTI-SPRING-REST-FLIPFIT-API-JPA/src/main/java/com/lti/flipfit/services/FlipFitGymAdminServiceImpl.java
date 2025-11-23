package com.lti.flipfit.services;

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.*;
import com.lti.flipfit.exceptions.center.*;
import com.lti.flipfit.exceptions.slots.*;
import com.lti.flipfit.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymAdminService interface.
 */

@Service
public class FlipFitGymAdminServiceImpl implements FlipFitGymAdminService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymAdminServiceImpl.class);
    @Autowired
    private FlipFitGymCenterRepository centerRepo;

    @Autowired
    private FlipFitGymSlotRepository slotRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    @Override
    @Transactional
    public String approveSlot(Long slotId) {
        GymSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with ID: " + slotId));

        if (slot.getIsActive()) {
            return "Slot is already active.";
        }

        // Validate time overlap with other ACTIVE slots
        List<GymSlot> existingSlots = slotRepo.findByCenterCenterIdAndIsActive(slot.getCenter().getCenterId(), true);
        boolean overlap = existingSlots.stream().anyMatch(existing -> timesOverlap(
                existing.getStartTime(),
                existing.getEndTime(),
                slot.getStartTime(),
                slot.getEndTime()));

        if (overlap) {
            throw new SlotAlreadyExistsException("An active slot already exists in this time range");
        }

        slot.setIsActive(true);
        slot.setStatus("AVAILABLE");
        slotRepo.save(slot);
        logger.info("Slot approved with ID: {}", slotId);
        return "Slot approved successfully.";
    }

    @Override
    public List<GymSlot> getPendingSlots(Long centerId) {
        logger.info("Fetching pending slots for center ID: {}", centerId);
        return slotRepo.findByCenterCenterIdAndIsActive(centerId, false);
    }

    @Override
    public List<GymCenter> getAllCenters() {
        logger.info("Fetching all centers");
        return centerRepo.findAll();
    }

    @Override
    public GymCenter getCenterById(Long centerId) {
        logger.info("Fetching center with ID: {}", centerId);
        return centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException(
                        "Center " + centerId + " not found"));
    }

    private boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    @Override
    @Transactional
    public String approveOwner(Long ownerId) {
        GymOwner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new InvalidInputException("Owner not found with ID: " + ownerId));

        if (owner.isApproved()) {
            return "Owner is already approved.";
        }

        owner.setApproved(true);
        ownerRepo.save(owner);
        logger.info("Owner approved with ID: {}", ownerId);
        return "Owner approved successfully.";
    }

    @Override
    public List<GymOwner> getPendingOwners() {
        logger.info("Fetching pending owners");
        return ownerRepo.findAll().stream()
                .filter(owner -> !owner.isApproved())
                .toList();
    }

    @Override
    @Transactional
    public String approveCenter(Long centerId) {
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found with ID: " + centerId));

        if (center.getIsActive()) {
            return "Center is already active.";
        }

        center.setIsActive(true);
        centerRepo.save(center);
        logger.info("Center approved with ID: {}", centerId);
        return "Center approved successfully.";
    }

    @Override
    public List<GymCenter> getPendingCenters() {
        logger.info("Fetching pending centers");
        return centerRepo.findByIsActive(false);
    }

    @Override
    @Transactional
    public void deleteCenter(Long centerId) {
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center not found with ID: " + centerId);
        }
        centerRepo.deleteById(centerId);
        logger.info("Center deleted with ID: {}", centerId);
    }

    @Override
    @Transactional
    public void deleteSlot(Long slotId) {
        if (!slotRepo.existsById(slotId)) {
            throw new SlotNotFoundException("Slot not found with ID: " + slotId);
        }
        slotRepo.deleteById(slotId);
        logger.info("Slot deleted with ID: {}", slotId);
    }
}
