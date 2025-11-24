package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for gym owners to manage bookings and centers.
 */
@RestController
@RequestMapping("/owner")
public class FlipFitGymOwnerController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymOwnerController.class);

    private final FlipFitGymOwnerService service;

    public FlipFitGymOwnerController(FlipFitGymOwnerService service) {
        this.service = service;
    }

    /**
     * Marks a pending booking as approved by the gym owner.
     *
     * @param bookingId The ID of the booking to approve.
     * @return A success message.
     */
    @RequestMapping(value = "/approve-booking/{bookingId}", method = RequestMethod.POST)
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
        logger.info("Received request to approve booking with ID: {}", bookingId);
        if (bookingId == null) {
            throw new InvalidInputException("Booking ID cannot be empty");
        }
        service.approveBooking(bookingId);
        return ResponseEntity.ok("Booking approved successfully");
    }

    /**
     * Creates a new center linked to the specified owner.
     *
     * @param ownerId The ID of the owner adding the center.
     * @param center  The GymCenter object containing center details.
     * @return The created GymCenter object.
     */
    @RequestMapping(value = "/add-center/{ownerId}", method = RequestMethod.POST)
    public ResponseEntity<GymCenter> addCenter(
            @PathVariable Long ownerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to add center for owner ID: {}", ownerId);

        if (ownerId == null) {
            throw new InvalidInputException("Owner ID cannot be empty");
        }
        return ResponseEntity.ok(service.addCenter(center, ownerId));
    }

    /**
     * Updates information of a center managed by the owner.
     *
     * @param centerId The ID of the center to update.
     * @param center   The GymCenter object containing updated details.
     * @return The updated GymCenter object.
     */
    @RequestMapping(value = "/update-center/{centerId}", method = RequestMethod.PUT)
    public ResponseEntity<GymCenter> updateCenter(
            @PathVariable Long centerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to update center with ID: {}", centerId);

        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        center.setCenterId(centerId); // Ensure ID is set from path
        return ResponseEntity.ok(service.updateCenter(center));
    }

    /**
     * Retrieves every booking associated with the given centerId.
     *
     * @param centerId The ID of the center to view bookings for.
     * @return A list of GymBooking objects.
     */
    @RequestMapping(value = "/all-bookings/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> viewAllBookings(@PathVariable Long centerId) {
        logger.info("Received request to view all bookings for center ID: {}", centerId);
        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        return ResponseEntity.ok(service.viewAllBookings(centerId));
    }

    /**
     * Retrieves all centers owned by a specific owner.
     *
     * @param ownerId The ID of the owner.
     * @return A list of GymCenter objects owned by the user.
     */
    @RequestMapping(value = "/centers/{ownerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getCentersByOwner(@PathVariable Long ownerId) {
        logger.info("Received request to get centers for owner ID: {}", ownerId);
        if (ownerId == null) {
            throw new InvalidInputException("Owner ID cannot be empty");
        }
        return ResponseEntity.ok(service.getCentersByOwner(ownerId));
    }

    /**
     * Adds a new slot to a center (Pending Approval).
     *
     * @param centerId The ID of the center to add the slot to.
     * @param slot     The GymSlot object containing slot details.
     * @return A success message.
     */
    @RequestMapping(value = "/add-slot/{centerId}", method = RequestMethod.POST)
    public ResponseEntity<String> addSlot(@PathVariable Long centerId, @RequestBody GymSlot slot) {
        logger.info("Received request to add slot to center ID: {}", centerId);
        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        service.addSlot(slot, centerId);
        return ResponseEntity.ok("Slot added successfully. Waiting for Admin approval.");
    }
}
