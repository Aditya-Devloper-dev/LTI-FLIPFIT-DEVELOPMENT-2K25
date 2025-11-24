package com.lti.flipfit.repository;

/**
 * Author : FlipFit Development Team
 * Version : 1.0
 * Description : JPA repository for Gym Customer operations.
 */

import com.lti.flipfit.entity.GymCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymCustomerRepository extends JpaRepository<GymCustomer, Long> {

    /**
     * Checks if a customer exists for a given user ID.
     *
     * @param userId The ID of the user.
     * @return True if a customer exists, false otherwise.
     */
    boolean existsByUser_UserId(Long userId);

    /**
     * Finds a customer by their user ID.
     *
     * @param userId The ID of the user.
     * @return The GymCustomer entity associated with the user ID.
     */
    GymCustomer findByUser_UserId(Long userId);
}
