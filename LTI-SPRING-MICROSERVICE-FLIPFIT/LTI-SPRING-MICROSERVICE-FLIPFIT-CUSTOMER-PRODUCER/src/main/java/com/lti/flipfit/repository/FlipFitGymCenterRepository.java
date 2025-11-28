package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for GymCenter operations.
 */
@Repository
public interface FlipFitGymCenterRepository extends JpaRepository<GymCenter, Long> {

}
