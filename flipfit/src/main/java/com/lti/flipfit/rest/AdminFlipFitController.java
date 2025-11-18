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
 * Description : Controller for admin operations such as approving centers.
 */
@RestController
@RequestMapping("/admin")
public class AdminFlipFitController {

    private AdminFlipFitService adminService;

    public AdminFlipFitController(AdminFlipFitService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/center")
    public ResponseEntity<String> createCenter(@RequestBody GymCenter center) {
        return null;
    }

    @PostMapping("/slot/{centerId}")
    public ResponseEntity<String> createSlot(
            @PathVariable String centerId,
            @RequestBody Slot slot) {
        return null;
    }

    @GetMapping("/centers")
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        return null;
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<GymCenter> getCenterById(@PathVariable String centerId) {
        return null;
    }
}
