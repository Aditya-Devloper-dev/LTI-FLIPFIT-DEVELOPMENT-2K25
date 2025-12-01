package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
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
     * @methodname - toggleCenterActive
     * @description - Toggles the active status of a center.
     * @param - centerId The ID of the center.
     * @param - ownerId The ID of the owner.
     * @return - A success message.
     */
    @RequestMapping(value = "/toggle-center-active/{centerId}/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> toggleCenterActive(@PathVariable Long centerId, @PathVariable Long ownerId) {
        logger.info("Received request to toggle active status for center ID: {} by owner ID: {}", centerId, ownerId);
        boolean isActive = service.toggleCenterActive(centerId, ownerId);
        String status = isActive ? "ACTIVE" : "INACTIVE";
        return ResponseEntity.ok("Center is now " + status);
    }

    /**
     * @methodname - toggleSlotActive
     * @description - Toggles the active status of a slot.
     * @param - slotId The ID of the slot.
     * @param - ownerId The ID of the owner.
     * @return - A success message.
     */
    @RequestMapping(value = "/toggle-slot-active/{slotId}/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> toggleSlotActive(@PathVariable Long slotId, @PathVariable Long ownerId) {
        logger.info("Received request to toggle active status for slot ID: {} by owner ID: {}", slotId, ownerId);
        boolean isActive = service.toggleSlotActive(slotId, ownerId);
        String status = isActive ? "ACTIVE" : "INACTIVE";
        return ResponseEntity.ok("Slot is now " + status);
    }

    /**
     * @methodname - addCenter
     * @description - Creates a new center linked to the specified owner.
     * @param - ownerId The ID of the owner adding the center.
     * @param - center The GymCenter object containing center details.
     * @return - The created GymCenter object.
     */
    @RequestMapping(value = "/add-center/{ownerId}", method = RequestMethod.POST)
    public ResponseEntity<GymCenter> addCenter(
            @PathVariable Long ownerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to add center for owner ID: {}", ownerId);
        return ResponseEntity.ok(service.addCenter(center, ownerId));
    }

    /**
     * @methodname - updateCenter
     * @description - Updates information of a center managed by the owner.
     * @param - centerId The ID of the center to update.
     * @param - ownerId The ID of the owner.
     * @param - center The GymCenter object containing updated details.
     * @return - The updated GymCenter object.
     */
    @RequestMapping(value = "/update-center/{centerId}/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<GymCenter> updateCenter(
            @PathVariable Long centerId,
            @PathVariable Long ownerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to update center with ID: {} for owner ID: {}", centerId, ownerId);
        center.setCenterId(centerId); // Ensure ID is set from path
        return ResponseEntity.ok(service.updateCenter(center, ownerId));
    }

    /**
     * @methodname - viewAllBookings
     * @description - Retrieves every booking associated with the given centerId.
     * @param - centerId The ID of the center to view bookings for.
     * @return - A list of GymBooking objects.
     */
    @RequestMapping(value = "/all-bookings/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> viewAllBookings(@PathVariable Long centerId) {
        logger.info("Received request to view all bookings for center ID: {}", centerId);
        return ResponseEntity.ok(service.viewAllBookings(centerId));
    }

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects owned by the user.
     */
    @RequestMapping(value = "/centers/{ownerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getCentersByOwner(@PathVariable Long ownerId) {
        logger.info("Received request to get centers for owner ID: {}", ownerId);
        return ResponseEntity.ok(service.getCentersByOwner(ownerId));
    }

    /**
     * @methodname - addSlot
     * @description - Adds a new slot to a center (Pending Approval).
     * @param - centerId The ID of the center to add the slot to.
     * @param - ownerId The ID of the owner.
     * @param - slot The GymSlot object containing slot details.
     * @return - A success message.
     */
    @RequestMapping(value = "/add-slot/{centerId}/{ownerId}", method = RequestMethod.POST)
    public ResponseEntity<String> addSlot(@PathVariable Long centerId, @PathVariable Long ownerId,
            @RequestBody GymSlot slot) {
        logger.info("Received request to add slot to center ID: {} for owner ID: {}", centerId, ownerId);
        service.addSlot(slot, centerId, ownerId);
        return ResponseEntity.ok("Slot added successfully. Waiting for Admin approval.");
    }
}
