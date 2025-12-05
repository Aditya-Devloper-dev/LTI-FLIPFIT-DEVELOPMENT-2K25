package com.lti.flipfit.scheduler;

import com.lti.flipfit.dao.FlipFitGymSlotDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author :
 * Version : 1.0
 * Description : Scheduler to manage recurring tasks for Gym Slots.
 */
@Component
public class SlotScheduler {

    @Autowired
    private FlipFitGymSlotDAO slotDAO;

    /**
     * Resets the available seats of all active slots to their total capacity.
     * Runs every day at midnight (00:00:00).
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void resetSlotCapacity() {
        System.out.println("Starting scheduled task: Resetting slot capacities...");
        slotDAO.resetAvailableSeatsForActiveSlots();
        System.out.println("Scheduled task completed: All active slots capacity reset.");
    }
}
