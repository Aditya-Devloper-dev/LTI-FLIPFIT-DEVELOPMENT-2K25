package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymWaitlist;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Waitlist operations.
 */
public interface FlipFitGymWaitlistService {

    /**
     * @methodname - joinWaitlist
     * @description - Adds a customer to the waitlist for a specific slot.
     * @param - customerId The ID of the customer.
     * @param - slotId The ID of the slot.
     * @return - A success message with the waitlist ID.
     */
    String joinWaitlist(Long customerId, Long slotId);

    /**
     * @methodname - cancelWaitlist
     * @description - Cancels a waitlist entry.
     * @param - waitlistId The unique identifier of the waitlist entry.
     * @return - A success message.
     */
    String cancelWaitlist(String waitlistId);

    /**
     * @methodname - getWaitlistByCustomer
     * @description - Retrieves the waitlist entries for a specific customer.
     * @param - customerId The ID of the customer.
     * @return - A list of waitlist entries for the customer.
     */
    List<GymWaitlist> getWaitlistByCustomer(Long customerId);
}
