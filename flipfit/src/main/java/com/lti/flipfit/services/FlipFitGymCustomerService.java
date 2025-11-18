package com.lti.flipfit.services;

import java.util.List;
import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Customer functions like searching slots and making bookings.
 */
public interface FlipFitGymCustomerService {

    List<Map<String, Object>> viewAvailability(String centerId, String date);

    String bookSlot(String customerId, String slotId, String centerId);

    boolean cancelBooking(String bookingId);

}
