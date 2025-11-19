package com.lti.flipfit.rest;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymAdminService;
import com.lti.flipfit.validator.CenterValidator;
import com.lti.flipfit.validator.SlotValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for admin operations such as creating centers, adding slots,
 *               updating center details, and retrieving center information.
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
     * @Description: Accepts center details, validates input, and forwards it to the service layer
     * @MethodParameters: GymCenter center
     * @Exception: Throws InvalidInputException if validation fails
     */
    @PostMapping("/center")
    public ResponseEntity<String> createCenter(@RequestBody GymCenter center) {

        CenterValidator.validateCreateCenter(center);

        String response = adminService.createCenter(center);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: createSlot
     * @Description: Accepts slot details and adds a slot under the given center
     * @MethodParameters: centerId -> Center unique ID, Slot slot -> slot details
     * @Exception: Throws InvalidInputException for invalid input, center not found handled in service
     */
    @PostMapping("/slot/{centerId}")
    public ResponseEntity<String> createSlot(@PathVariable String centerId,
                                             @RequestBody Slot slot) {

        if (centerId == null || centerId.isBlank()) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        SlotValidator.validateSlot(slot);

        String response = adminService.createSlot(centerId, slot);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: getAllCenters
     * @Description: Retrieves the complete list of registered gym centers
     * @MethodParameters: None
     * @Exception: Propagates any service-level exceptions
     */
    @GetMapping("/centers")
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        List<GymCenter> centers = adminService.getAllCenters();
        return ResponseEntity.ok(centers);
    }

    /*
     * @Method: getCenterById
     * @Description: Fetches information for a specific center using its ID
     * @MethodParameters: centerId -> unique identifier for the center
     * @Exception: Throws InvalidInputException if centerId is blank
     */
    @GetMapping("/center/{centerId}")
    public ResponseEntity<GymCenter> getCenterById(@PathVariable String centerId) {

        if (centerId == null || centerId.isBlank()) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        GymCenter center = adminService.getCenterById(centerId);
        return ResponseEntity.ok(center);
    }
}
