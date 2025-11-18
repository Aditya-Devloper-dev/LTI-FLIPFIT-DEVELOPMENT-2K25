package com.lti.flipfit.rest;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.beans.Slot;
import com.lti.flipfit.services.AdminFlipFitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminFlipFitController {

    private final AdminFlipFitService adminService;

    public AdminFlipFitController(AdminFlipFitService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/center")
    public ResponseEntity<String> createCenter(@RequestBody GymCenter center) {
        String response = adminService.createCenter(center);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/slot/{centerId}")
    public ResponseEntity<String> createSlot(@PathVariable String centerId,
                                             @RequestBody Slot slot) {
        String response = adminService.createSlot(centerId, slot);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/centers")
    public ResponseEntity<List<GymCenter>> getAllCenters() {
        List<GymCenter> centers = adminService.getAllCenters();
        return ResponseEntity.ok(centers);
    }

    @GetMapping("/center/{centerId}")
    public ResponseEntity<GymCenter> getCenterById(@PathVariable String centerId) {
        GymCenter center = adminService.getCenterById(centerId);
        return ResponseEntity.ok(center);
    }
}
