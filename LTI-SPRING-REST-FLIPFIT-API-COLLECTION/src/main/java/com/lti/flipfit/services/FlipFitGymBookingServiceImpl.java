package com.lti.flipfit.services;

import com.lti.flipfit.beans.Booking;
import com.lti.flipfit.exceptions.bookings.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlipFitGymBookingServiceImpl implements FlipFitGymBookingService {

    // simple in-memory booking store
    private final Map<String, Booking> bookingStore = new HashMap<>();

    /*
     * @Method: bookSlot
     * @Description: Books a new slot after validating duplicate bookings and booking limits.
     * @MethodParameters: booking -> Booking request payload
     * @Exception: Throws BookingAlreadyExistsException, BookingLimitExceededException
     */
    @Override
    public String bookSlot(Booking booking) {

        // Check if same user already booked same slot
        boolean alreadyBooked = bookingStore.values().stream()
                .anyMatch(b ->
                        b.getCustomerId().equals(booking.getCustomerId()) &&
                                b.getSlotId().equals(booking.getSlotId())
                );

        if (alreadyBooked) {
            throw new BookingAlreadyExistsException(
                    "User already booked this slot: " + booking.getSlotId()
            );
        }
        // Limit: Max 3 bookings per user (example rule)
        long userBookingsCount = bookingStore.values().stream()
                .filter(b -> b.getCustomerId().equals(booking.getCustomerId()))
                .count();

        if (userBookingsCount >= 3) {
            throw new BookingLimitExceededException(
                    "User exceeded max allowed bookings (3 per day)"
            );
        }
        // Create booking ID
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        booking.setCreatedAt(LocalDateTime.now());

        bookingStore.put(bookingId, booking);

        return "Booking successful with ID: " + bookingId;
    }

    /*
     * @Method: cancelBooking
     * @Description: Cancels an existing booking after validating booking existence and state.
     * @MethodParameters: bookingId -> Unique booking identifier
     * @Exception: Throws BookingNotFoundException, BookingCancellationNotAllowedException
     */
    @Override
    public String cancelBooking(String bookingId) {

        Booking existing = bookingStore.get(bookingId);

        if (existing == null) {
            throw new BookingNotFoundException("Booking not found: " + bookingId);
        }

        // Example rule: booking cannot be cancelled after status is COMPLETED
        if ("COMPLETED".equalsIgnoreCase(existing.getStatus())) {
            throw new BookingCancellationNotAllowedException(
                    "Booking cannot be cancelled after completion"
            );
        }

        bookingStore.remove(bookingId);
        return "Booking " + bookingId + " cancelled successfully";
    }

    /*
     * @Method: getUserBookings
     * @Description: Returns all bookings made by a user.
     * @MethodParameters: userId -> user ID
     * @Exception: None (empty list returned if no bookings)
     */
    @Override
    public List<Booking> getUserBookings(String userId) {

        List<Booking> list = new ArrayList<>();

        for (Booking b : bookingStore.values()) {
            if (b.getCustomerId().equals(userId)) {
                list.add(b);
            }
        }

        return list;
    }
}
