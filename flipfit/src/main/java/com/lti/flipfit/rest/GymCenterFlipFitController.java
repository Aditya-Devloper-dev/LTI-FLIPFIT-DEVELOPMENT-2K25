package com.lti.flipfit.rest;

import com.lti.flipfit.services.GymCenterFlipFitService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for gym center slot and information management.
 */
@RestController
@RequestMapping("/gym-center")
public class GymCenterFlipFitController {

    private final GymCenterFlipFitService service;

    public GymCenterFlipFitController(GymCenterFlipFitService service) {
        this.service = service;
    }

    @GetMapping("/slots")
    public Object getSlotsByDate(@RequestParam String centerId,
                                 @RequestParam String date) {
        return service.getSlotsByDate(centerId, date);
    }

    @PutMapping("/update/{centerId}")
    public boolean updateCenterInfo(@PathVariable String centerId) {
        return service.updateCenterInfo(centerId);
    }
}

