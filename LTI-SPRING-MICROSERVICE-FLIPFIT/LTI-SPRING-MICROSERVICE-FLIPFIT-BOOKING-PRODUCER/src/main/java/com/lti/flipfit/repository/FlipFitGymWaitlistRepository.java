package com.lti.flipfit.repository;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for Gym Waitlist operations.
 */

import com.lti.flipfit.entity.GymSlot;
import com.lti.flipfit.entity.GymWaitlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlipFitGymWaitlistRepository extends JpaRepository<GymWaitlist, String> {

    /**
     * Finds the oldest waitlist entry for a given slot.
     * Assumes lower position or earlier creation time means higher priority.
     * Here we order by createdAt ascending (First Come First Serve).
     *
     * @param slot The slot entity.
     * @return An Optional containing the first waitlist entry if found.
     */
    Optional<GymWaitlist> findFirstBySlotOrderByCreatedAtAsc(GymSlot slot);
}
