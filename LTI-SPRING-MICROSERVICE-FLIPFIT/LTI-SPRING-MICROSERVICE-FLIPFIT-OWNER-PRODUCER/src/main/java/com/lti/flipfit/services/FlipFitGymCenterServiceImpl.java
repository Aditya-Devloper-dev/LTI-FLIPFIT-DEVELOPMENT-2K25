package com.lti.flipfit.services;

/**
 * Author : 
 * Version : 1.0
 * Description : Implementation of the FlipFitGymCenterService interface.
 */

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.center.*;
import com.lti.flipfit.repository.*;
import com.lti.flipfit.validator.OwnerValidator;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.cache.annotation.Cacheable;

@Service
public class FlipFitGymCenterServiceImpl implements FlipFitGymCenterService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCenterServiceImpl.class);

    @Autowired
    private FlipFitGymCenterRepository centerRepo;

    @Autowired
    private FlipFitGymSlotRepository slotRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    @Autowired
    private FlipFitGymBookingRepository bookingRepo;

    @Autowired
    private OwnerValidator ownerValidator;

    public FlipFitGymCenterServiceImpl(FlipFitGymCenterRepository centerRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymOwnerRepository ownerRepo,
            FlipFitGymBookingRepository bookingRepo,
            OwnerValidator ownerValidator) {
        this.centerRepo = centerRepo;
        this.slotRepo = slotRepo;
        this.ownerRepo = ownerRepo;
        this.bookingRepo = bookingRepo;
        this.ownerValidator = ownerValidator;
    }

    /**
     * @methodname - getSlotsByCenterId
     * @description - Fetches all slots for a given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     * @throws CenterNotFoundException if center does not exist.
     */
    @Override
    public List<GymSlot> getSlotsByCenterId(Long centerId) {
        logger.info("Fetching all slots for center ID: {}", centerId);
        if (!centerRepo.existsById(centerId)) {
            throw new CenterNotFoundException("Center " + centerId + " not found");
        }
        return slotRepo.findByCenterCenterId(centerId);
    }

    /**
     * @methodname - getCenterById
     * @description - Fetches a gym center by its ID.
     * @param - centerId The ID of the gym center.
     * @return - The GymCenter entity.
     * @throws CenterNotFoundException if center does not exist.
     */
    @Override
    @Cacheable(value = "gymCenter", key = "#centerId")
    public GymCenter getCenterById(Long centerId) {
        logger.info("Fetching center details for center ID: {}", centerId);
        return centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center " + centerId + " not found"));
    }

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects.
     */
    @Override
    public List<GymCenter> getCentersByOwner(Long ownerId) {
        logger.info("Fetching centers for owner ID: {}", ownerId);
        if (!ownerRepo.existsById(ownerId)) {
            throw new UserNotFoundException("Owner not found");
        }
        return centerRepo.findByOwnerOwnerId(ownerId);
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
     * @methodname - updateCenter
     * @description - Updates an existing center's details.
     * @param - center The GymCenter object with updated details.
     * @param - ownerId The ID of the owner.
     * @return - The updated GymCenter object.
     */
    @Override
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

}
