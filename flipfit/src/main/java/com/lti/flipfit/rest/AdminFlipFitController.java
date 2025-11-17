package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for admin operations such as approving centers.
 */
@RestController
@RequestMapping("/admin")
public class AdminFlipFitController {

    @PostMapping("/approve-center/{centerId}")
    public boolean approveCenter(@PathVariable String centerId) {
        return false; // placeholder
    }
}
