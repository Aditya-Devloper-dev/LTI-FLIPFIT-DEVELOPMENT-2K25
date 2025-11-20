package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymCustomerRepository extends JpaRepository<GymCustomer, Long> {

    // Optional helper queries (use when needed)
    boolean existsByUser_UserId(Long userId);

    GymCustomer findByUser_UserId(Long userId);
}
