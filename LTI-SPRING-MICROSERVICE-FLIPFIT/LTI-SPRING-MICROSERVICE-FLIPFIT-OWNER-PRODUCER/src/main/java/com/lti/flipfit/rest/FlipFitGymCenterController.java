package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.services.FlipFitGymCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for gym center slot and information management.
 */
@RestController
@RequestMapping("/gym-center")
public class FlipFitGymCenterController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCenterController.class);

    private final FlipFitGymCenterService service;

    public FlipFitGymCenterController(FlipFitGymCenterService service) {
        this.service = service;
    }

    /**
     * @methodname - getSlotsByCenterId
     * @description - Retrieves all slots for the given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     */
    @RequestMapping(value = "/slots/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getSlotsByCenterId(@PathVariable Long centerId) {
        logger.info("Received request to get slots for center ID: {}", centerId);
        return ResponseEntity.ok(service.getSlotsByCenterId(centerId));
    }

    /**
     * @methodname - getCenterById
     * @description - Retrieves a gym center by its ID.
     * @param - centerId The ID of the gym center.
     * @return - The GymCenter entity.
     */
    @RequestMapping(value = "/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<GymCenter> getCenterById(@PathVariable Long centerId) {
        logger.info("Received request to get center details for ID: {}", centerId);
        return ResponseEntity.ok(service.getCenterById(centerId));
    }

    /**
     * @methodname - getCentersByOwner
     * @description - Retrieves all centers owned by a specific owner.
     * @param - ownerId The ID of the owner.
     * @return - A list of GymCenter objects owned by the user.
     */
    @RequestMapping(value = "/owner/{ownerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getCentersByOwner(@PathVariable Long ownerId) {
        logger.info("Received request to get centers for owner ID: {}", ownerId);
        return ResponseEntity.ok(service.getCentersByOwner(ownerId));
    }

    /**
     * @methodname - viewAllBookings
     * @description - Retrieves every booking associated with the given centerId.
     * @param - centerId The ID of the center to view bookings for.
     * @return - A list of GymBooking objects.
     */
    @RequestMapping(value = "/bookings/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> viewAllBookings(@PathVariable Long centerId) {
        logger.info("Received request to view all bookings for center ID: {}", centerId);
        return ResponseEntity.ok(service.viewAllBookings(centerId));
    }

    /**
     * @methodname - updateCenter
     * @description - Updates information of a center managed by the owner.
     * @param - centerId The ID of the center to update.
     * @param - ownerId The ID of the owner.
     * @param - center The GymCenter object containing updated details.
     * @return - The updated GymCenter object.
     */
    @RequestMapping(value = "/update/{centerId}/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<GymCenter> updateCenter(
            @PathVariable Long centerId,
            @PathVariable Long ownerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to update center with ID: {} for owner ID: {}", centerId, ownerId);
        center.setCenterId(centerId); // Ensure ID is set from path
        return ResponseEntity.ok(service.updateCenter(center, ownerId));
    }
}
