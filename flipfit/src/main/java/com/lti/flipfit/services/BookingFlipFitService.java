package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles booking creation, updates, and cancellations.
 */
public interface BookingFlipFitService {

    String bookSlot(String customerId, String centerId, String slotId);

    boolean cancelBooking(String bookingId);

    boolean confirmBooking(String bookingId);

    boolean requestOwnerApproval(String bookingId);

}
