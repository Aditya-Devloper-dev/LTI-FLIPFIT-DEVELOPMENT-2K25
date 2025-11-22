package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymCenterService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Author :
 * Version : 1.0
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

        if (date == null || date.isBlank()) {
            throw new InvalidInputException("Date cannot be empty");
        }

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new InvalidInputException("Invalid date format. Expected yyyy-MM-dd");
        }

        return service.getSlotsByDate(centerId, parsedDate);
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

        return service.updateCenterInfo(centerId, center);
    }
}
