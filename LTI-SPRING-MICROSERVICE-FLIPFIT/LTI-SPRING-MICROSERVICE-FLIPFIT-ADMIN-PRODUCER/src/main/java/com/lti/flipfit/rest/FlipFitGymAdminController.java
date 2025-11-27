package com.lti.flipfit.rest;

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for admin operations such as creating centers,
 * adding slots, updating center details, and retrieving center information.
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class FlipFitGymAdminController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymAdminController.class);

    private final FlipFitGymAdminService adminService;

    public FlipFitGymAdminController(FlipFitGymAdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * @methodname - approveSlot
     * @description - Approves a pending gym slot.
     * @param - slotId Unique identifier of the slot.
     * @return - ResponseEntity containing the result of the operation.
     */

    @RequestMapping(value = "/approve-slot/{slotId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveSlot(@PathVariable Long slotId) {
        logger.info("Received request to approve slot with ID: {}", slotId);
        return ResponseEntity.ok(adminService.approveSlot(slotId));
    }

    /**
     * @methodname - getPendingSlots
     * @description - Retrieves a list of all pending gym slots for a center.
     * @param - centerId Unique identifier of the center.
     * @return - ResponseEntity containing a list of pending GymSlot objects.
     */

    @RequestMapping(value = "/pending-slots/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getPendingSlots(@PathVariable Long centerId) {
        logger.info("Received request to get pending slots for center ID: {}", centerId);
        return ResponseEntity.ok(adminService.getPendingSlots(centerId));
    }

    /**
     * @methodname - getAllCenters
     * @description - Retrieves the complete list of registered gym centers.
     * @return - ResponseEntity containing a list of all GymCenter objects.
     */

    @RequestMapping(value = "/centers", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        logger.info("Received request to get all centers");
        return ResponseEntity.ok(adminService.getAllCenters());
    }

    /**
     * @methodname - getCenterById
     * @description - Fetches information for a specific center using its ID.
     * @param - centerId Unique identifier for the center.
     * @return - ResponseEntity containing the GymCenter object.
     */

    @RequestMapping(value = "/center/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<GymCenter> getCenterById(@PathVariable Long centerId) {
        logger.info("Received request to get center with ID: {}", centerId);

        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        return ResponseEntity.ok(adminService.getCenterById(centerId));
    }

    /**
     * @methodname - approveOwner
     * @description - Approves a pending gym owner.
     * @param - ownerId Unique identifier of the owner.
     * @return - ResponseEntity containing the result of the operation.
     */

    @RequestMapping(value = "/approve-owner/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveOwner(@PathVariable Long ownerId) {
        logger.info("Received request to approve owner with ID: {}", ownerId);
        return ResponseEntity.ok(adminService.approveOwner(ownerId));
    }

    /**
     * @methodname - getPendingOwners
     * @description - Retrieves a list of all pending gym owners.
     * @return - ResponseEntity containing a list of pending GymOwner objects.
     */

    @RequestMapping(value = "/pending-owners", method = RequestMethod.GET)
    public ResponseEntity<List<GymOwner>> getPendingOwners() {
        logger.info("Received request to get pending owners");
        return ResponseEntity.ok(adminService.getPendingOwners());
    }

    /**
     * @methodname - approveCenter
     * @description - Approves a pending gym center.
     * @param - centerId Unique identifier of the center.
     * @return - ResponseEntity containing the result of the operation.
     */

    @RequestMapping(value = "/approve-center/{centerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveCenter(@PathVariable Long centerId) {
        logger.info("Received request to approve center with ID: {}", centerId);
        return ResponseEntity.ok(adminService.approveCenter(centerId));
    }

    /**
     * @methodname - getPendingCenters
     * @description - Retrieves a list of all pending gym centers.
     * @return - ResponseEntity containing a list of pending GymCenter objects.
     */

    @RequestMapping(value = "/pending-centers", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getPendingCenters() {
        logger.info("Received request to get pending centers");
        return ResponseEntity.ok(adminService.getPendingCenters());
    }

    /**
     * @methodname - deleteCenter
     * @description - Deletes a gym center.
     * @param - centerId Unique identifier of the center.
     * @return - ResponseEntity containing the result of the operation.
     */

    @RequestMapping(value = "/delete-center/{centerId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCenter(@PathVariable Long centerId) {
        logger.info("Received request to delete center with ID: {}", centerId);
        adminService.deleteCenter(centerId);
        return ResponseEntity.ok("Center deleted successfully");
    }

    /**
     * @methodname - deleteSlot
     * @description - Deletes a gym slot.
     * @param - slotId Unique identifier of the slot.
     * @return - ResponseEntity containing the result of the operation.
     */

    @RequestMapping(value = "/delete-slot/{slotId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSlot(@PathVariable Long slotId) {
        logger.info("Received request to delete slot with ID: {}", slotId);
        adminService.deleteSlot(slotId);
        return ResponseEntity.ok("Slot deleted successfully");
    }

    /**
     * @methodname - viewPayments
     * @description - Retrieves payments based on filter type and date.
     * @param - filterType The type of filter (ALL, MONTHLY, WEEKLY, DAILY).
     * @param - date The specific date for DAILY filter (YYYY-MM-DD).
     * @return - ResponseEntity containing a list of GymPayment objects.
     */
    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ResponseEntity<List<GymPayment>> viewPayments(
            @RequestParam(defaultValue = "ALL") String filterType,
            @RequestParam(required = false) String date) {
        logger.info("Received request to view payments with filter: {} and date: {}", filterType, date);
        return ResponseEntity.ok(adminService.viewPayments(filterType, date));
    }
}
