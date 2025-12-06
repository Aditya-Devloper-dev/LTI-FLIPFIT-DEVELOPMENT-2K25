package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Customer operations.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymSlot;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Customer functions like searching slots and making bookings.
 */
public interface FlipFitGymCustomerService {

    GymCustomer getProfile(Long customerId);

    List<GymBooking> getCustomerBookings(Long customerId);

    List<GymSlot> getAllAvailableSlots();
}
