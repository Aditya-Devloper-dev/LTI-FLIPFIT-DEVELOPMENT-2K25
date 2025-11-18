package com.lti.flipfit.rest;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.services.BookingFlipFitService;
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
public class BookingFlipFitController {

    private final BookingFlipFitService bookingService;

    public BookingFlipFitController(BookingFlipFitService bookingService) {
        this.bookingService = bookingService;
    }

    /*
     * @Method: Booking a slot
     * @Description: Accepts booking details and forwards them to the service layer for processing
     * @MethodParameters: Booking booking
     * @Exception: Throws exceptions for slot full, conflicts, or invalid booking data
     */

    @PostMapping("/book")
    public ResponseEntity<String> bookSlot(@RequestBody Booking booking) {
        String response = bookingService.bookSlot(booking);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: Cancelling a booking
     * @Description: Cancels a booking using its bookingId and updates slot availability
     * @MethodParameters: String bookingId
     * @Exception: Throws exceptions if booking is not found or cancellation is not allowed
     */

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        String response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    /*
     * @Method: Retrieving bookings for a user
     * @Description: Returns all bookings made by the specified user
     * @MethodParameters: String userId
     * @Exception: Throws exceptions if user not found or retrieval fails
     */

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String userId) {
        List<Booking> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }
}
