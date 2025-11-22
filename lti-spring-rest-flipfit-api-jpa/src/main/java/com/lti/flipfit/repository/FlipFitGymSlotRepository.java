package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author : <Your Name>
 * Version : 1.0
 * Repository : GymSlotRepository
 * Description : JPA repository for performing CRUD operations on GymSlot.
 * Supports retrieval of all slots under a specific center.
 */

@Repository
public interface FlipFitGymSlotRepository extends JpaRepository<GymSlot, Long> {

    List<GymSlot> findByCenterCenterId(Long centerId);
}
