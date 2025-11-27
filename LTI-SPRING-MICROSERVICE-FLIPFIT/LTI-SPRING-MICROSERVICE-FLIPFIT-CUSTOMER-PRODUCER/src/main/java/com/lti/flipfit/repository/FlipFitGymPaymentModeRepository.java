package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymPaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlipFitGymPaymentModeRepository extends JpaRepository<GymPaymentMode, String> {
    Optional<GymPaymentMode> findByModeName(String modeName);
}
