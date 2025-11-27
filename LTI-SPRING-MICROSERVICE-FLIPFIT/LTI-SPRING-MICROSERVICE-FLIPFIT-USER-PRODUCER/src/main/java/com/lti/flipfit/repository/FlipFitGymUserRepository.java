package com.lti.flipfit.repository;

/**
 * Author : 
 * Version : 1.0
 * Description : JPA repository for Gym User operations.
 */

import com.lti.flipfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlipFitGymUserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a user exists with the given email (case-insensitive).
     *
     * @param email The email to check.
     * @return True if a user exists, false otherwise.
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Checks if a user exists with the given email and phone number.
     *
     * @param email       The email to check.
     * @param phoneNumber The phone number to check.
     * @return True if a user exists, false otherwise.
     */
    boolean existsByEmailIgnoreCaseAndPhoneNumber(String email, String phoneNumber);

    /**
     * Finds a user by their email (case-insensitive).
     *
     * @param email The email of the user.
     * @return The User entity if found, null otherwise.
     */
    User findByEmailIgnoreCase(String email);
}
