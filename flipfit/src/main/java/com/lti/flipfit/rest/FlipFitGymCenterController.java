package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymCenterService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for gym center slot and information management.
 */
@RestController
@RequestMapping("/gym-center")
public class FlipFitGymCenterController {

    private final FlipFitGymCenterService service;

    public FlipFitGymCenterController(FlipFitGymCenterService service) {
        this.service = service;
    }

    /*
     * @Method: Fetching slots for a center by date
     * @Description: Retrieves all slots for the given centerId on the specified date
     * @MethodParameters: String centerId, String date
     * @Exception: Throws exceptions if centerId/date is invalid or slot data retrieval fails
     */

    @GetMapping("/slots")
    public Object getSlotsByDate(@RequestParam String centerId,
                                 @RequestParam String date) {
        return service.getSlotsByDate(centerId, date);
    }

    /*
     * @Method: Updating gym center information
     * @Description: Updates the details of a center identified by centerId
     * @MethodParameters: String centerId
     * @Exception: Throws exceptions if center does not exist or update cannot be performed
     */

    @PutMapping("/update/{centerId}")
    public boolean updateCenterInfo(@PathVariable String centerId) {
        return service.updateCenterInfo(centerId);
    }
}

