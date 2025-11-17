package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Customer functions like searching slots and making bookings.
 */
public interface CustomerFlipFitService {

    Object viewAvailability(String centerId, String date);

    String bookSlot(String customerId, String slotId, String centerId);

    boolean cancelBooking(String bookingId);

}
