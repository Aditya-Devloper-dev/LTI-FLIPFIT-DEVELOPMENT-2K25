package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymAdminService;
import com.lti.flipfit.validator.CenterValidator;
import com.lti.flipfit.validator.SlotValidator;
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
     * @Method: createCenter
     * 
     * @Description: Accepts center details, validates input, and forwards it to the
     * service layer
     * 
     * @MethodParameters: GymCenter center
     * 
     * @Exception: Throws InvalidInputException if validation fails
     */
    @RequestMapping(value = "/center", method = RequestMethod.POST)
    public ResponseEntity<String> createCenter(@RequestBody GymCenter center) {
        CenterValidator.validateCreateCenter(center);
        return ResponseEntity.ok(adminService.createCenter(center));
    }

    /*
     * @Method: createSlot
     * 
     * @Description: Accepts slot details and adds a slot under the given center
     * 
     * @MethodParameters: centerId -> Center unique ID, Slot slot -> slot details
     * 
     * @Exception: Throws InvalidInputException for invalid input, center not found
     * handled in service
     */
    @RequestMapping(value = "/slot/{centerId}", method = RequestMethod.POST)
    public ResponseEntity<String> createSlot(@PathVariable Long centerId,
            @RequestBody GymSlot gymSlot) {

        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        SlotValidator.validateSlot(gymSlot);

        return ResponseEntity.ok(adminService.createSlot(centerId, gymSlot));
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
}
