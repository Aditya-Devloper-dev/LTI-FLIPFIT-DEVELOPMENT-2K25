package com.lti.flipfit.rest;

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCenter;
import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.services.FlipFitGymOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @MethodParameters: bookingId
     * 
     * @Exception: BookingNotFoundException
     */
    @RequestMapping(value = "/approve-booking/{bookingId}", method = RequestMethod.POST)
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
        if (bookingId == null) {
            throw new InvalidInputException("Booking ID cannot be empty");
        }
        service.approveBooking(bookingId);
        return ResponseEntity.ok("Booking approved successfully");
    }

    /*
     * @Method: Adding a center under an owner
     * 
     * @Description: Creates a new center linked to the specified owner
     * 
     * @MethodParameters: ownerId, GymCenter object
     * 
     * @Exception: UserNotFoundException
     */
    @RequestMapping(value = "/add-center/{ownerId}", method = RequestMethod.POST)
    public ResponseEntity<GymCenter> addCenter(
            @PathVariable Long ownerId,
            @RequestBody GymCenter center) {

        if (ownerId == null) {
            throw new InvalidInputException("Owner ID cannot be empty");
        }
        return ResponseEntity.ok(service.addCenter(center, ownerId));
    }

    /*
     * @Method: Updating center details by owner
     * 
     * @Description: Updates information of a center managed by the owner
     * 
     * @MethodParameters: centerId, GymCenter object
     * 
     * @Exception: CenterNotFoundException
     */
    @RequestMapping(value = "/update-center/{centerId}", method = RequestMethod.PUT)
    public ResponseEntity<GymCenter> updateCenter(
            @PathVariable Long centerId,
            @RequestBody GymCenter center) {

        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        center.setCenterId(centerId); // Ensure ID is set from path
        return ResponseEntity.ok(service.updateCenter(center));
    }

    /*
     * @Method: Viewing all bookings for a center
     * 
     * @Description: Retrieves every booking associated with the given centerId
     * 
     * @MethodParameters: centerId
     * 
     * @Exception: CenterNotFoundException
     */
    @RequestMapping(value = "/all-bookings/{centerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymBooking>> viewAllBookings(@PathVariable Long centerId) {
        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        return ResponseEntity.ok(service.viewAllBookings(centerId));
    }

    /*
     * @Method: View all centers by owner
     * 
     * @Description: Retrieves all centers owned by a specific owner
     * 
     * @MethodParameters: ownerId
     * 
     * @Exception: UserNotFoundException
     */
    @RequestMapping(value = "/centers/{ownerId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymCenter>> getCentersByOwner(@PathVariable Long ownerId) {
        if (ownerId == null) {
            throw new InvalidInputException("Owner ID cannot be empty");
        }
        return ResponseEntity.ok(service.getCentersByOwner(ownerId));
    }

    /*
     * @Method: Add Slot
     * 
     * @Description: Adds a new slot to a center (Pending Approval)
     * 
     * @MethodParameters: centerId, GymSlot
     * 
     * @Exception: CenterNotFoundException
     */
    @RequestMapping(value = "/add-slot/{centerId}", method = RequestMethod.POST)
    public ResponseEntity<String> addSlot(@PathVariable Long centerId, @RequestBody GymSlot slot) {
        if (centerId == null) {
            throw new InvalidInputException("Center ID cannot be empty");
        }
        service.addSlot(slot, centerId);
        return ResponseEntity.ok("Slot added successfully. Waiting for Admin approval.");
    }
}
