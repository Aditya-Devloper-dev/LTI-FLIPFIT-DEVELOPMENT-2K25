package com.lti.flipfit.repository;

/**
 * Author : Shiny Sunaina
 * Version : 1.0
 * Description : JPA repository for Gym User operations.
 */

import com.lti.flipfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlipFitGymUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndPhoneNumber(String email, String phoneNumber);

    User findByEmailIgnoreCase(String email);
}
