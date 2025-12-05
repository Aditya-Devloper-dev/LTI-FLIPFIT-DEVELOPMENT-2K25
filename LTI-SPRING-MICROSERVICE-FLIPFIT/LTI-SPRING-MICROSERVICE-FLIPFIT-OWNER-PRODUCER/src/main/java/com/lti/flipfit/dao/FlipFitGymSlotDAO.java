package com.lti.flipfit.dao;

/**
 * Author :
 * Version : 1.0
 * Description : Interface for GymSlot DAO operations.
 */
public interface FlipFitGymSlotDAO {
    void resetAvailableSeatsForActiveSlots();

    boolean checkSlotOverlap(Long centerId, java.time.LocalTime startTime, java.time.LocalTime endTime);
}
