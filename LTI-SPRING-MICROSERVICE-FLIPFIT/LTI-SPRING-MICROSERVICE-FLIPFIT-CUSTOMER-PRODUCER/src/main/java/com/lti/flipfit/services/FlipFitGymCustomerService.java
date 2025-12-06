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
 * Description : Service interface for handling Customer operations.
 */
public interface FlipFitGymCustomerService {

    /**
     * @methodname - getAllAvailableSlots
     * @description - Retrieves all active gym slots.
     * @return - A list of GymSlot entities.
     */
    List<GymSlot> getAllAvailableSlots();

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

    /**
     * @methodname - getCustomerByUserId
     * @description - Fetches customer details by user ID.
     * @param - userId The unique ID of the user.
     * @return - The GymCustomer entity.
     */
    GymCustomer getCustomerByUserId(Long userId);
}
