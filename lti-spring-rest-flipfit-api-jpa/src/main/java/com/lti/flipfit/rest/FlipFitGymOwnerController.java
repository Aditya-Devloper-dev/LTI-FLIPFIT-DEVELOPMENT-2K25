package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymOwnerService;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for gym owners to manage bookings and centers.
 */
@RestController
@RequestMapping("/owner")
public class FlipFitGymOwnerController {

    private final FlipFitGymOwnerService service;

    public FlipFitGymOwnerController(FlipFitGymOwnerService service) {
        this.service = service;
    }

    /*
     * @Method: Approving a booking
     * 
     * @Description: Marks a pending booking as approved by the gym owner
     * 
     * @MethodParameters: String bookingId
     * 
     * @Exception: Throws exceptions if booking is not found or owner is not
     * authorized
     */

    /*
     * @Method: Approving a booking
     * 
     * @Description: Marks a pending booking as approved by the gym owner
     * 
     * @MethodParameters: String bookingId
     * 
     * @Exception: Throws exceptions if booking is not found or owner is not
     * authorized
     */

    @RequestMapping(value = "/approve-booking/{bookingId}", method = RequestMethod.POST)
    public boolean approveBooking(@PathVariable String bookingId) {
        return service.approveBooking(bookingId);
    }

    /*
     * @Method: Adding a center under an owner
     * 
     * @Description: Links an existing center to the specified owner account
     * 
     * @MethodParameters: String ownerId, String centerId
     * 
     * @Exception: Throws exceptions if owner/center is invalid or mapping already
     * exists
     */

    @RequestMapping(value = "/add-center", method = RequestMethod.POST)
    public boolean addCenter(@RequestParam String ownerId,
            @RequestParam String centerId) {
        return service.addCenter(ownerId, centerId);
    }

    /*
     * @Method: Updating center details by owner
     * 
     * @Description: Updates information of a center managed by the owner
     * 
     * @MethodParameters: String centerId
     * 
     * @Exception: Throws exceptions if center does not exist or owner lacks
     * permission
     */

    @RequestMapping(value = "/update-center/{centerId}", method = RequestMethod.PUT)
    public boolean updateCenter(@PathVariable String centerId) {
        return service.updateCenter(centerId);
    }

    /*
     * @Method: Viewing all bookings for a center
     * 
     * @Description: Retrieves every booking associated with the given centerId
     * 
     * @MethodParameters: String centerId
     * 
     * @Exception: Throws exceptions if center is invalid or booking data retrieval
     * fails
     */

    @RequestMapping(value = "/all-bookings/{centerId}", method = RequestMethod.GET)
    public Object viewAllBookings(@PathVariable String centerId) {
        return service.viewAllBookings(centerId);
    }
}
