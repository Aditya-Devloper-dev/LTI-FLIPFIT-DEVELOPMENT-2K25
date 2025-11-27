package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Customer operations.
 */

import com.lti.flipfit.entity.GymBooking;
import com.lti.flipfit.entity.GymCustomer;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Customer operations.
 */
public interface FlipFitGymCustomerService {

    /**
     * @methodname - viewAvailability
     * @description - Checks the availability of slots for a given center on a
     *              specific date.
     * @param - centerId The ID of the gym center.
     * @param - date The date to check availability for.
     * @return - A list of maps containing slot details and availability.
     */
    List<Map<String, Object>> viewAvailability(String centerId, String date);

    /**
     * @methodname - getProfile
     * @description - Retrieves the profile of a customer.
     * @param - customerId The ID of the customer.
     * @return - The GymCustomer entity.
     */
    GymCustomer getProfile(Long customerId);

    /**
     * @methodname - getCustomerBookings
     * @description - Retrieves all bookings made by a customer.
     * @param - customerId The ID of the customer.
     * @return - A list of GymBooking entities.
     */
    List<GymBooking> getCustomerBookings(Long customerId);

    /**
     * @methodname - viewAllGyms
     * @description - Retrieves all active gym centers.
     * @return - A list of active GymCenter entities.
     */
    List<com.lti.flipfit.entity.GymCenter> viewAllGyms();
}
