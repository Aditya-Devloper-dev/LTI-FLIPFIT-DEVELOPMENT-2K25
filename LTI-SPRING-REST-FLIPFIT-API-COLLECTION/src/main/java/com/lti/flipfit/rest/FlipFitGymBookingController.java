package com.lti.flipfit.rest;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.services.FlipFitGymBookingService;
import com.lti.flipfit.validator.BookingValidator;
import com.lti.flipfit.exceptions.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for managing the complete booking lifecycle including
 *               booking creation, cancellation, and fetching user-specific bookings.
 */

@RestController
@RequestMapping("/booking")
public class FlipFitGymBookingController {

    private final FlipFitGymBookingService bookingService;

    public FlipFitGymBookingController(FlipFitGymBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /*
     * @Method: bookSlot
     * @Description: Accepts booking details, validates them, then forwards to service layer
     * @MethodParameters: Booking booking
     * @Exception: Throws InvalidBookingException for missing data,
     *             and service-level exceptions for conflict/full/slot not found
     */
    @PostMapping("/book")
    public ResponseEntity<String> bookSlot(@RequestBody Booking booking) {

        BookingValidator.validateBookingRequest(booking);

        String response = bookingService.bookSlot(booking);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: cancelBooking
     * @Description: Cancels a booking by its ID and updates availability
     * @MethodParameters: bookingId -> unique booking ID
     * @Exception: Throws InvalidInputException if bookingId is empty,
     *             booking-related exceptions if booking doesn't exist
     */
    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {

        if (bookingId == null || bookingId.isBlank()) {
            throw new InvalidInputException("Booking ID cannot be empty");
        }

        String response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: getUserBookings
     * @Description: Fetches all bookings created by a specific user
     * @MethodParameters: userId -> unique customer ID
     * @Exception: Throws InvalidInputException for blank userId,
     *             service-level exceptions if user not found
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {

        if (userId == null || userId.isBlank()) {
            throw new InvalidInputException("User ID cannot be empty");
        }

        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
