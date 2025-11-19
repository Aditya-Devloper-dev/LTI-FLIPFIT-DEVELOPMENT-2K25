package com.lti.flipfit.rest;

import com.lti.flipfit.beans.GymCenter;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymCenterService;
import com.lti.flipfit.validator.CenterValidator;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
     * @Method: getSlotsByDate
     * @Description: Retrieves all slots available for the given center on the selected date
     * @MethodParameters: centerId -> unique center ID, date -> yyyy-MM-dd
     * @Exception: Throws InvalidInputException for blank centerId/date
     */
    @GetMapping("/slots")
    public Object getSlotsByDate(@RequestParam String centerId,
                                 @RequestParam String date) {

        if (centerId == null || centerId.isBlank()) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        if (date == null || date.isBlank()) {
            throw new InvalidInputException("Date cannot be empty");
        }

        // Convert string date to LocalDate
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
     * @Description: Updates the provided center information for the given centerId
     * @MethodParameters: centerId -> unique center ID, GymCenter center -> new data
     * @Exception: Throws InvalidInputException for blank ID or missing data
     */
    @PutMapping("/update/{centerId}")
    public boolean updateCenterInfo(@PathVariable String centerId,
                                    @RequestBody GymCenter center) {

        if (centerId == null || centerId.isBlank()) {
            throw new InvalidInputException("Center ID cannot be empty");
        }

        CenterValidator.validateCreateCenter(center); // reusing the same validator

        return service.updateCenterInfo(centerId, center);
    }
}
