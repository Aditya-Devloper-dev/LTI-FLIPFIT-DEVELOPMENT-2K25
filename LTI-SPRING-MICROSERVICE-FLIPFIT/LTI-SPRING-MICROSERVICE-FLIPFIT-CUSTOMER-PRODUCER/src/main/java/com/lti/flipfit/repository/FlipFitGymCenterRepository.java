package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for GymCenter operations.
 */
@Repository
public interface FlipFitGymCenterRepository extends JpaRepository<GymCenter, Long> {

    /**
     * Finds all active gym centers.
     *
     * @return List of active GymCenter entities.
     */
    List<GymCenter> findByIsActiveTrue();
}
