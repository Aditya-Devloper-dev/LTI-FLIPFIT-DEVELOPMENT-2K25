package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for gym center slot and information management.
 */
@RestController
@RequestMapping("/gym-center")
public class FlipFitGymCenterController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymCenterController.class);

    private final FlipFitGymCenterService service;

    public FlipFitGymCenterController(FlipFitGymCenterService service) {
        this.service = service;
    }

    /*
     * @Method: getSlotsByDate
     * 
     * @Description: Retrieves all slots available for the given center on the
     * selected date
     * 
     * @MethodParameters: centerId -> unique center ID, date -> yyyy-MM-dd
     * 
     * @Exception: Throws InvalidInputException for blank centerId/date
     */
    @RequestMapping(value = "/slots", method = RequestMethod.GET)
    public Object getSlotsByDate(@RequestParam Long centerId,
            @RequestParam String date) {
        logger.info("Received request to get slots for center ID: {} on date: {}", centerId, date);
        return service.getSlotsByDate(centerId, date);
    }

    /*
     * @Method: getSlotsByCenterId
     * 
     * @Description: Retrieves all slots for the given center
     * 
     * @MethodParameters: centerId -> unique center ID
     * 
     * @Exception: Throws InvalidInputException for blank centerId
     */
    @RequestMapping(value = "/slots/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getSlotsByCenterId(@PathVariable Long centerId) {
        logger.info("Received request to get slots for center ID: {}", centerId);
        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        return ResponseEntity.ok(service.getSlotsByCenterId(centerId));
    }

    /*
     * @Method: updateCenterInfo
     * 
     * @Description: Updates the provided center information for the given centerId
     * 
     * @MethodParameters: centerId -> unique center ID, GymCenter center -> new data
     * 
     * @Exception: Throws InvalidInputException for blank ID or missing data
     */
    @RequestMapping(value = "/update-center/{centerId}", method = RequestMethod.PUT)
    public boolean updateCenterInfo(@PathVariable Long centerId,
            @RequestBody GymCenter center) {
        logger.info("Received request to update center info for center ID: {}", centerId);

        return service.updateCenterInfo(centerId, center);
    }
}
