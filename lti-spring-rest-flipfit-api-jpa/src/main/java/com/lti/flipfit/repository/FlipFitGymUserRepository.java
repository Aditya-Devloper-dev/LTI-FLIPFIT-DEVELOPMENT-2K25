package com.lti.flipfit.repository;

import com.lti.flipfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlipFitGymUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndPhoneNumber(String email, String phoneNumber);

    User findByEmailIgnoreCase(String email);
}
