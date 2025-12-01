package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.dao.FlipFitGymOwnerDAO;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.validator.OwnerValidator;

import com.lti.flipfit.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymOwnerService interface.
 */
@Service
public class FlipFitGymOwnerServiceImpl implements FlipFitGymOwnerService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymOwnerServiceImpl.class);

    private final FlipFitGymOwnerRepository ownerRepo;
    private final FlipFitGymCenterRepository centerRepo;
    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymOwnerDAO ownerDAO;
    private final OwnerValidator ownerValidator;
    private final FlipFitGymBookingRepository bookingRepo;

    public FlipFitGymOwnerServiceImpl(FlipFitGymOwnerRepository ownerRepo,
            FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymOwnerDAO ownerDAO,
            OwnerValidator ownerValidator,
            FlipFitGymBookingRepository bookingRepo) {
        this.ownerRepo = ownerRepo;
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.ownerDAO = ownerDAO;
        this.ownerValidator = ownerValidator;
        this.bookingRepo = bookingRepo;
    }

    /**
     * @methodname - toggleCenterActive
     * @description - Toggles the active status of a center.
     * @param - centerId The ID of the center.
     * @param - ownerId The ID of the owner.
     */
    @Override
    @Transactional
    @CacheEvict(value = "ownerCache", allEntries = true)
    public boolean toggleCenterActive(Long centerId, Long ownerId) {
        logger.info("Toggling active status for center ID: {}", centerId);
        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        ownerValidator.validateToggleCenter(center, ownerId);

        boolean updated = ownerDAO.toggleCenterStatus(centerId, ownerId);
        if (!updated) {
            throw new InvalidInputException("Failed to toggle center status");
        }

        // Fetch updated status to return correct value
        return !center.getIsActive();
    }

    /**
     * @methodname - toggleSlotActive
     * @description - Toggles the active status of a slot.
     * @param - slotId The ID of the slot.
     * @param - ownerId The ID of the owner.
     * @return - The new active status (true/false).
     */
    @Override
    @Transactional
    @CacheEvict(value = "centerSlots", allEntries = true)
    public boolean toggleSlotActive(Long slotId, Long ownerId) {
        logger.info("Toggling active status for slot ID: {}", slotId);
        GymSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new InvalidInputException("Slot not found"));

        ownerValidator.validateToggleSlot(slot, ownerId);

        boolean updated = ownerDAO.toggleSlotStatus(slotId, ownerId);
        if (!updated) {
            throw new InvalidInputException("Failed to toggle slot status");
        }

        return !slot.getIsActive();
    }

    /**
     * @methodname - addCenter
     * @description - Adds a new center for a specific owner.
     * @param - center The GymCenter object to add.
     * @param - ownerId The ID of the owner.
     * @return - The added GymCenter object.
     */
    @Override
    @CacheEvict(value = "ownerCache", key = "#ownerId.toString()")
    public GymCenter addCenter(GymCenter center, Long ownerId) {
        logger.info("Adding center for owner ID: {}", ownerId);
        GymOwner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        ownerValidator.validateAddCenter(owner);

        center.setOwner(owner);
        return centerRepo.save(center);
    }

    /**
     * @methodname - updateCenter
     * @description - Updates an existing center's details.
     * @param - center The GymCenter object with updated details.
     * @param - ownerId The ID of the owner.
     * @return - The updated GymCenter object.
     */
    @Override
    @CacheEvict(value = "ownerCache", key = "#ownerId.toString()")
    public GymCenter updateCenter(GymCenter center, Long ownerId) {
        logger.info("Updating center with ID: {}", center.getCenterId());
        if (center.getCenterId() == null) {
            throw new CenterNotFoundException("Center ID is required for update");
        }

        GymCenter existingCenter = centerRepo.findById(center.getCenterId())
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        ownerValidator.validateUpdateCenter(existingCenter, ownerId);

        // Update fields (only non-null)
        if (center.getCenterName() != null)
            existingCenter.setCenterName(center.getCenterName());
        if (center.getCity() != null)
            existingCenter.setCity(center.getCity());
        if (center.getContactNumber() != null)
            existingCenter.setContactNumber(center.getContactNumber());

        return centerRepo.save(existingCenter);
    }

    /**
     * @methodname - viewAllBookings
     * @description - Retrieves all bookings for a specific center.
     * @param - centerId The ID of the center.
     * @return - A list of GymBooking objects.
     */
    @Override
    @Cacheable(value = "ownerCache", key = "'bookings-' + #centerId")
    public List<GymBooking> viewAllBookings(Long centerId) {
        logger.info("Fetching all bookings for center ID: {}", centerId);
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center not found");
        }
        return bookingRepo.findByCenterCenterId(centerId);
    }

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects.
     */
    @Override
    @Cacheable(value = "ownerCache", key = "#ownerId.toString()")
    public List<GymCenter> getCentersByOwner(Long ownerId) {
        logger.info("Fetching centers for owner ID: {}", ownerId);
        if (!ownerRepo.existsById(ownerId)) {
            throw new UserNotFoundException("Owner not found");
        }
        return centerRepo.findByOwnerOwnerId(ownerId);
    }

    /**
     * @methodname - addSlot
     * @description - Adds a new slot to a center.
     * @param - slot The GymSlot object to add.
     * @param - centerId The ID of the center.
     * @param - ownerId The ID of the owner.
     */
    @Override
    @CacheEvict(value = "centerSlots", allEntries = true)
    public void addSlot(GymSlot slot, Long centerId, Long ownerId) {
        logger.info("Adding slot to center ID: {}", centerId);

        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        ownerValidator.validateAddSlot(slot, center, ownerId);

        slot.setCenter(center);
        slot.setIsActive(false); // Default to inactive until approved
        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("PENDING");

        slotRepo.save(slot);
    }
}
