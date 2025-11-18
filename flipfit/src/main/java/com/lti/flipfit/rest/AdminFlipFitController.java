package com.lti.flipfit.rest;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.services.AdminFlipFitService;
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
public class AdminFlipFitController {

    private final AdminFlipFitService adminService;

    public AdminFlipFitController(AdminFlipFitService adminService) {
        this.adminService = adminService;
    }

    /*
     * @Method: Creating a new gym center
     * @Description: Accepts center details and forwards it to the service layer for creation
     * @MethodParameters: GymCenter center
     * @Exception: Throws exceptions if validation fails or center already exists
     */

    @PostMapping("/center")
    public ResponseEntity<String> createCenter(@RequestBody GymCenter center) {
        String response = adminService.createCenter(center);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: Creating a slot for a specific center
     * @Description: Adds a new slot under the given center after validation
     * @MethodParameters: String centerId, Slot slot
     * @Exception: Throws exceptions if center not found or slot configuration is invalid
     */

    @PostMapping("/slot/{centerId}")
    public ResponseEntity<String> createSlot(@PathVariable String centerId,
                                             @RequestBody Slot slot) {
        String response = adminService.createSlot(centerId, slot);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: Listing all gym centers
     * @Description: Retrieves a complete list of registered gym centers from the system
     * @MethodParameters: None
     * @Exception: Throws exceptions if retrieval fails
     */

    @GetMapping("/centers")
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        List<GymCenter> centers = adminService.getAllCenters();
        return ResponseEntity.ok(centers);
    }

    /*
     * @Method: Fetching details of a specific center
     * @Description: Returns center metadata by centerId
     * @MethodParameters: String centerId
     * @Exception: Throws exceptions if the center does not exist
     */

    @GetMapping("/center/{centerId}")
    public ResponseEntity<GymCenter> getCenterById(@PathVariable String centerId) {
        GymCenter center = adminService.getCenterById(centerId);
        return ResponseEntity.ok(center);
    }
}
