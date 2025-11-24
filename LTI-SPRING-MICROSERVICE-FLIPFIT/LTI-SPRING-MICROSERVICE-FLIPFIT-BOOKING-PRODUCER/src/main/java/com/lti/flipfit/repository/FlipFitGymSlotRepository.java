package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for performing CRUD operations on GymSlot.
 * Supports retrieval of all slots under a specific center.
 */

@Repository
public interface FlipFitGymSlotRepository extends JpaRepository<GymSlot, Long> {

    /**
     * Finds all slots for a specific center.
     *
     * @param centerId The ID of the center.
     * @return A list of slots for the center.
     */
    List<GymSlot> findByCenterCenterId(Long centerId);

    /**
     * Finds all slots for a specific center with a specific active status.
     *
     * @param centerId The ID of the center.
     * @param isActive The active status of the slots.
     * @return A list of slots matching the criteria.
     */
    List<GymSlot> findByCenterCenterIdAndIsActive(Long centerId, Boolean isActive);
}
