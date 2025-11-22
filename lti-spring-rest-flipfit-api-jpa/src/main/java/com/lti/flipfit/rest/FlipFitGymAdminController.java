package com.lti.flipfit.rest;

import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for admin operations such as creating centers,
 * adding slots, updating center details, and retrieving center information.
 */

@RestController
@RequestMapping("/admin")
public class FlipFitGymAdminController {

    private final FlipFitGymAdminService adminService;

    public FlipFitGymAdminController(FlipFitGymAdminService adminService) {
        this.adminService = adminService;
    }

    /*
     * @Method: approveSlot
     * 
     * @Description: Approves a pending gym slot
     * 
     * @MethodParameters: slotId -> Unique identifier of the slot
     * 
     * @Exception: Throws SlotNotFoundException if slot not found
     */
    @RequestMapping(value = "/approve-slot/{slotId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveSlot(@PathVariable Long slotId) {
        return ResponseEntity.ok(adminService.approveSlot(slotId));
    }

    /*
     * @Method: getPendingSlots
     * 
     * @Description: Retrieves a list of all pending gym slots for a center
     * 
     * @MethodParameters: centerId
     * 
     * @Exception: None
     */
    @RequestMapping(value = "/pending-slots/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getPendingSlots(@PathVariable Long centerId) {
        return ResponseEntity.ok(adminService.getPendingSlots(centerId));
    }

    /*
     * @Method: getAllCenters
     * 
     * @Description: Retrieves the complete list of registered gym centers
     * 
     * @MethodParameters: None
     * 
     * @Exception: Propagates any service-level exceptions
     */
    @RequestMapping(value = "/centers", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        return ResponseEntity.ok(adminService.getAllCenters());
    }

    /*
     * @Method: getCenterById
     * 
     * @Description: Fetches information for a specific center using its ID
     * 
     * @MethodParameters: centerId -> unique identifier for the center
     * 
     * @Exception: Throws InvalidInputException if centerId is blank
     */
    @RequestMapping(value = "/center/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<GymCenter> getCenterById(@PathVariable Long centerId) {

        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        return ResponseEntity.ok(adminService.getCenterById(centerId));
    }

    /*
     * @Method: approveOwner
     * 
     * @Description: Approves a pending gym owner
     * 
     * @MethodParameters: ownerId -> Unique identifier of the owner
     * 
     * @Exception: Throws InvalidInputException if owner not found
     */
    @RequestMapping(value = "/approve-owner/{ownerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(adminService.approveOwner(ownerId));
    }

    /*
     * @Method: getPendingOwners
     * 
     * @Description: Retrieves a list of all pending gym owners
     * 
     * @MethodParameters: None
     * 
     * @Exception: None
     */
    @RequestMapping(value = "/pending-owners", method = RequestMethod.GET)
    public ResponseEntity<List<GymOwner>> getPendingOwners() {
        return ResponseEntity.ok(adminService.getPendingOwners());
    }

    /*
     * @Method: approveCenter
     * 
     * @Description: Approves a pending gym center
     * 
     * @MethodParameters: centerId -> Unique identifier of the center
     * 
     * @Exception: Throws CenterNotFoundException if center not found
     */
    @RequestMapping(value = "/approve-center/{centerId}", method = RequestMethod.PUT)
    public ResponseEntity<String> approveCenter(@PathVariable Long centerId) {
        return ResponseEntity.ok(adminService.approveCenter(centerId));
    }

    /*
     * @Method: getPendingCenters
     * 
     * @Description: Retrieves a list of all pending gym centers
     * 
     * @MethodParameters: None
     * 
     * @Exception: None
     */
    @RequestMapping(value = "/pending-centers", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getPendingCenters() {
        return ResponseEntity.ok(adminService.getPendingCenters());
    }

    /*
     * @Method: deleteCenter
     * 
     * @Description: Deletes a gym center
     * 
     * @MethodParameters: centerId -> Unique identifier of the center
     * 
     * @Exception: Throws CenterNotFoundException if center not found
     */
    @RequestMapping(value = "/delete-center/{centerId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCenter(@PathVariable Long centerId) {
        adminService.deleteCenter(centerId);
        return ResponseEntity.ok("Center deleted successfully");
    }

    /*
     * @Method: deleteSlot
     * 
     * @Description: Deletes a gym slot
     * 
     * @MethodParameters: slotId -> Unique identifier of the slot
     * 
     * @Exception: Throws SlotNotFoundException if slot not found
     */
    @RequestMapping(value = "/delete-slot/{slotId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSlot(@PathVariable Long slotId) {
        adminService.deleteSlot(slotId);
        return ResponseEntity.ok("Slot deleted successfully");
    }
}
