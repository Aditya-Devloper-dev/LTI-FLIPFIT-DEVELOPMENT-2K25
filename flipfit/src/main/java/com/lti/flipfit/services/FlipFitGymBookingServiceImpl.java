package com.lti.flipfit.services;

import com.lti.flipfit.beans.Booking;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlipFitGymBookingServiceImpl implements FlipFitGymBookingService {

    // simple in-memory booking store
    private final Map<String, Booking> bookingStore = new HashMap<>();

    @Override
    public String bookSlot(Booking booking) {
        String id = UUID.randomUUID().toString();
        booking.setBookingId(id);
        bookingStore.put(id, booking);
        return "Booking successful with ID: " + id;
    }

    @Override
    public String cancelBooking(String bookingId) {
        if (bookingStore.containsKey(bookingId)) {
            bookingStore.remove(bookingId);
            return "Booking " + bookingId + " cancelled successfully";
        }
        return "Booking not found: " + bookingId;
    }

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
