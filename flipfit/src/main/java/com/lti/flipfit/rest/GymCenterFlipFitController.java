package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for gym center slot and information management.
 */
@RestController
@RequestMapping("/gym-center")
public class GymCenterFlipFitController {

    @GetMapping("/slots")
    public Object getSlotsByDate(@RequestParam String centerId,
                                 @RequestParam String date) {
        return null;
    }

    @PutMapping("/update/{centerId}")
    public boolean updateCenterInfo(@PathVariable String centerId) {
        return false;
    }
}
