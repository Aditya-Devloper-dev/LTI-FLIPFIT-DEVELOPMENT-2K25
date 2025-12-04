package com.lti.flipfit.repository;

import com.lti.flipfit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymUserRepository extends JpaRepository<User, Long> {
}
