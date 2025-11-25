package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.slots.SlotAlreadyExistsException;
import com.lti.flipfit.exceptions.slots.SlotNotFoundException;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

/**
 * Author : Shiny Sunaina
 * Version : 1.1
 * Description : Implementation of the FlipFitGymAdminService interface.
 * Handles business logic for admin operations.
 */

@Service
public class FlipFitGymAdminServiceImpl implements FlipFitGymAdminService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymAdminServiceImpl.class);

    private final FlipFitGymCenterRepository centerRepo;
    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymOwnerRepository ownerRepo;

    public FlipFitGymAdminServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymOwnerRepository ownerRepo) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.ownerRepo = ownerRepo;
    }

    /**
     * Approves a pending slot.
     * 
     * @param slotId The ID of the slot to approve.
     * @return A success message or error message.
     */
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

    /**
     * Retrieves all pending slots for a specific center.
     * 
     * @param centerId The ID of the center.
     * @return List of pending GymSlot objects.
     */
    @Override
    public List<GymSlot> getPendingSlots(Long centerId) {
        logger.info("Fetching pending slots for center ID: {}", centerId);
        return slotRepo.findByCenterCenterIdAndIsActive(centerId, false);
    }

    /**
     * Retrieves all registered gym centers.
     * 
     * @return List of all GymCenter objects.
     */
    @Override
    public List<GymCenter> getAllCenters() {
        logger.info("Fetching all centers");
        return centerRepo.findAll();
    }

    /**
     * Retrieves a specific center by its ID.
     * 
     * @param centerId The ID of the center.
     * @return The GymCenter object.
     * @throws CenterNotFoundException if the center is not found.
     */
    @Override
    public GymCenter getCenterById(Long centerId) {
        logger.info("Fetching center with ID: {}", centerId);
        return centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException(
                        "Center " + centerId + " not found"));
    }

    /**
     * Helper method to check if two time ranges overlap.
     * 
     * @param s1 Start time of first range.
     * @param e1 End time of first range.
     * @param s2 Start time of second range.
     * @param e2 End time of second range.
     * @return true if they overlap, false otherwise.
     */
    private boolean timesOverlap(LocalTime s1, LocalTime e1, LocalTime s2, LocalTime e2) {
        return s1.isBefore(e2) && s2.isBefore(e1);
    }

    /**
     * Approves a pending gym owner.
     * 
     * @param ownerId The ID of the owner to approve.
     * @return A success message or error message.
     */
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

    /**
     * Retrieves all pending gym owners.
     * 
     * @return List of pending GymOwner objects.
     */
    @Override
    public List<GymOwner> getPendingOwners() {
        logger.info("Fetching pending owners");
        return ownerRepo.findAll().stream()
                .filter(owner -> !owner.isApproved())
                .toList();
    }

    /**
     * Approves a pending gym center.
     * 
     * @param centerId The ID of the center to approve.
     * @return A success message.
     */
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

    /**
     * Retrieves all pending gym centers.
     * 
     * @return List of pending GymCenter objects.
     */
    @Override
    public List<GymCenter> getPendingCenters() {
        logger.info("Fetching pending centers");
        return centerRepo.findByIsActive(false);
    }

    /**
     * Deletes a gym center by its ID.
     * 
     * @param centerId The ID of the center to delete.
     * @throws CenterNotFoundException if the center is not found.
     */
    @Override
    @Transactional
    public void deleteCenter(Long centerId) {
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center not found with ID: " + centerId);
        }
        centerRepo.deleteById(centerId);
        logger.info("Center deleted with ID: {}", centerId);
    }

    /**
     * Deletes a gym slot by its ID.
     * 
     * @param slotId The ID of the slot to delete.
     * @throws SlotNotFoundException if the slot is not found.
     */
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
