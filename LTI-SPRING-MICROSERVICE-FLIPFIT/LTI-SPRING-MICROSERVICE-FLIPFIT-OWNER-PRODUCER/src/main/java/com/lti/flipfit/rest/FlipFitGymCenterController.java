package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
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

    /**
     * @methodname - getSlotsByDate
     * @description - Retrieves all slots available for the given center on the
     *              selected date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of GymSlot entities.
     */
    @RequestMapping(value = "/slots", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getSlotsByDate(@RequestParam Long centerId,
            @RequestParam String date) {
        logger.info("Received request to get slots for center ID: {} on date: {}", centerId, date);
        return ResponseEntity.ok(service.getSlotsByDate(centerId, date));
    }

    /**
     * @methodname - getSlotsByCenterId
     * @description - Retrieves all slots for the given center.
     * @param - centerId The ID of the gym center.
     * @return - A list of GymSlot entities.
     */
    @RequestMapping(value = "/slots/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymSlot>> getSlotsByCenterId(@PathVariable Long centerId) {
        logger.info("Received request to get slots for center ID: {}", centerId);
        return ResponseEntity.ok(service.getSlotsByCenterId(centerId));
    }
}
