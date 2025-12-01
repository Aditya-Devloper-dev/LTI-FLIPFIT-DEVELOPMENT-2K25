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
import com.lti.flipfit.client.FlipFitGymBookingClient;
import com.lti.flipfit.dao.FlipFitGymAdminDAO;
import com.lti.flipfit.dto.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lti.flipfit.utils.TimeUtils.timesOverlap;

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
    private final FlipFitGymBookingClient bookingClient;
    private final FlipFitGymAdminDAO adminDAO;
    private final NotificationProducer notificationProducer;

    public FlipFitGymAdminServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymOwnerRepository ownerRepo,
            FlipFitGymBookingClient bookingClient,
            FlipFitGymAdminDAO adminDAO,
            NotificationProducer notificationProducer) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.ownerRepo = ownerRepo;
        this.bookingClient = bookingClient;
        this.adminDAO = adminDAO;
        this.notificationProducer = notificationProducer;
    }

    /**
     * Approves a pending slot.
     * 
     * @param slotId The ID of the slot to approve.
     * @return A success message or error message.
     */
    @Override
    @Transactional
    @CacheEvict(value = "pendingSlots", allEntries = true)
    public String approveSlot(Long slotId) {
        GymSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with ID: " + slotId));

        if (Boolean.TRUE.equals(slot.getIsApproved())) {
            return "Slot is already approved.";
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

        adminDAO.approveSlot(slotId);
        logger.info("Slot approved with ID: {}", slotId);

        // Send Notification
        String ownerEmail = slot.getCenter().getOwner().getUser().getEmail();
        String message = "Congratulations! Your slot scheduled for " + slot.getStartTime() + " - " + slot.getEndTime()
                + " has been approved.";
        notificationProducer.sendNotification(new NotificationEvent(ownerEmail, message, "Slot Approved"));

        return "Slot approved successfully.";
    }

    /**
     * Retrieves all pending slots for a specific center.
     * 
     * @param centerId The ID of the center.
     * @return List of pending GymSlot objects.
     */
    @Override
    @Cacheable(value = "pendingSlots", key = "#centerId")
    public List<GymSlot> getPendingSlots(Long centerId) {
        logger.info("Fetching pending slots for center ID: {}", centerId);
        return adminDAO.findPendingSlots(centerId);
    }

    /**
     * Retrieves all registered gym centers.
     * 
     * @return List of all GymCenter objects.
     */
    @Override
    @Cacheable(value = "gymCenters")
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
    @Cacheable(value = "gymCenter", key = "#centerId")
    public GymCenter getCenterById(Long centerId) {
        logger.info("Fetching center with ID: {}", centerId);
        return centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException(
                        "Center " + centerId + " not found"));
    }

    /**
     * Approves a pending gym owner.
     * 
     * @param ownerId The ID of the owner to approve.
     * @return A success message or error message.
     */
    @Override
    @Transactional
    @CacheEvict(value = "pendingOwners", allEntries = true)
    public String approveOwner(Long ownerId) {
        GymOwner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new InvalidInputException("Owner not found with ID: " + ownerId));

        if (owner.isApproved()) {
            return "Owner is already approved.";
        }

        adminDAO.approveOwner(ownerId);
        logger.info("Owner approved with ID: {}", ownerId);

        // Send Notification
        String ownerEmail = owner.getUser().getEmail();
        String message = "Congratulations! Your Gym Owner account has been approved. You can now add centers and slots.";
        notificationProducer.sendNotification(new NotificationEvent(ownerEmail, message, "Account Approved"));

        return "Owner approved successfully.";
    }

    /**
     * Retrieves all pending gym owners.
     * 
     * @return List of pending GymOwner objects.
     */
    @Override
    @Cacheable(value = "pendingOwners")
    public List<GymOwner> getPendingOwners() {
        logger.info("Fetching pending owners");
        return adminDAO.findPendingOwners();
    }

    /**
     * Approves a pending gym center.
     * 
     * @param centerId The ID of the center to approve.
     * @return A success message.
     */
    @Override
    @Transactional
    @CacheEvict(value = { "pendingCenters", "gymCenters" }, allEntries = true)
    public String approveCenter(Long centerId) {
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found with ID: " + centerId));

        if (Boolean.TRUE.equals(center.getIsApproved())) {
            return "Center is already approved.";
        }

        adminDAO.approveCenter(centerId);
        logger.info("Center approved with ID: {}", centerId);

        // Send Notification
        String ownerEmail = center.getOwner().getUser().getEmail();
        String message = "Congratulations! Your center '" + center.getCenterName() + "' has been approved.";
        notificationProducer.sendNotification(new NotificationEvent(ownerEmail, message, "Center Approved"));

        return "Center approved successfully.";
    }

    /**
     * Retrieves all pending gym centers.
     * 
     * @return List of pending GymCenter objects.
     */
    @Override
    @Cacheable(value = "pendingCenters")
    public List<GymCenter> getPendingCenters() {
        logger.info("Fetching pending centers");
        return adminDAO.findPendingCenters();
    }

    /**
     * Deletes a gym center by its ID.
     * 
     * @param centerId The ID of the center to delete.
     * @throws CenterNotFoundException if the center is not found.
     */
    @Override
    @Transactional
    @CacheEvict(value = { "gymCenters", "gymCenter" }, allEntries = true, key = "#centerId")
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
    @CacheEvict(value = "pendingSlots", allEntries = true)
    public void deleteSlot(Long slotId) {
        if (!slotRepo.existsById(slotId)) {
            throw new SlotNotFoundException("Slot not found with ID: " + slotId);
        }
        slotRepo.deleteById(slotId);
        logger.info("Slot deleted with ID: {}", slotId);
    }

    /**
     * Retrieves payments based on filter type.
     *
     * @param filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param date       The specific date for DAILY filter (YYYY-MM-DD).
     * @return List of GymPayment objects.
     */
    @Override
    @Cacheable(value = "adminPayments", key = "{#filterType, #date}")
    public List<com.lti.flipfit.entity.GymPayment> viewPayments(String filterType, String date) {
        logger.info("Fetching payments via Feign Client with filter: {} and date: {}", filterType, date);
        return bookingClient.viewPayments(filterType, date);
    }
}
