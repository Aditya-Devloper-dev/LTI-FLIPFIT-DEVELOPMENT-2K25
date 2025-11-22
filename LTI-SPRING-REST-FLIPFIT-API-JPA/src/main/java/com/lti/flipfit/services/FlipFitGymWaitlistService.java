package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymWaitlist;
import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for handling Waitlist operations.
 */
public interface FlipFitGymWaitlistService {

    String joinWaitlist(Long customerId, Long slotId);

    String cancelWaitlist(String waitlistId);

    List<GymWaitlist> getWaitlistByCustomer(Long customerId);
}
