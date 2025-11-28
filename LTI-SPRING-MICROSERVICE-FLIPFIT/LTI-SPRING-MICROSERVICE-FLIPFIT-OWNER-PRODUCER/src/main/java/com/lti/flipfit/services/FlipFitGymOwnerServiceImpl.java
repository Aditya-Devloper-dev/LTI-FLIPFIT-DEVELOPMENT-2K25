package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.dao.FlipFitGymOwnerDAO;
import com.lti.flipfit.exceptions.bookings.BookingNotFoundException;
import com.lti.flipfit.exceptions.center.CenterNotFoundException;
import com.lti.flipfit.exceptions.user.UserNotFoundException;
import com.lti.flipfit.repository.FlipFitGymBookingRepository;
import com.lti.flipfit.repository.FlipFitGymCenterRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import com.lti.flipfit.repository.FlipFitGymSlotRepository;

import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.UnauthorizedAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

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
    private final FlipFitGymBookingRepository bookingRepo;
    private final FlipFitGymSlotRepository slotRepo;
    private final FlipFitGymOwnerDAO ownerDAO;

    public FlipFitGymOwnerServiceImpl(FlipFitGymOwnerRepository ownerRepo,
            FlipFitGymCenterRepository centerRepo,
            FlipFitGymBookingRepository bookingRepo,
            FlipFitGymSlotRepository slotRepo,
            FlipFitGymOwnerDAO ownerDAO) {
        this.ownerRepo = ownerRepo;
        this.centerRepo = centerRepo;
        this.bookingRepo = bookingRepo;
        this.slotRepo = slotRepo;
        this.ownerDAO = ownerDAO;
    }

    /**
     * @methodname - approveBooking
     * @description - Approves a booking by its ID.
     * @param - bookingId The ID of the booking to approve.
     * @return - True if approval was successful.
     */
    @Override
    @CacheEvict(value = "ownerCache", allEntries = true)
    public boolean approveBooking(Long bookingId) {
        logger.info("Approving booking with ID: {}", bookingId);
        GymBooking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        booking.setApprovedByOwner(true);
        bookingRepo.save(booking);
        return true;
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

        if (!owner.isApproved()) {
            throw new UnauthorizedAccessException("Owner approval is pending from Admin Side");
        }

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

        if (!existingCenter.getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this center");
        }

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
        return ownerDAO.findBookingsByCenterId(centerId);
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
        return ownerDAO.findCentersByOwnerId(ownerId);
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

        if (slot.getStartTime().isAfter(slot.getEndTime()) || slot.getStartTime().equals(slot.getEndTime())) {
            throw new InvalidInputException("Start time must be before end time");
        }

        GymCenter center = centerRepo.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found"));

        if (!center.getOwner().getOwnerId().equals(ownerId)) {
            throw new UnauthorizedAccessException("You do not own this center");
        }

        if (!center.getIsActive()) {
            throw new UnauthorizedAccessException("Center is not active yet");
        }

        slot.setCenter(center);
        slot.setIsActive(false); // Default to inactive until approved
        slot.setAvailableSeats(slot.getCapacity());
        slot.setStatus("PENDING");

        slotRepo.save(slot);
    }
}
