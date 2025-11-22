package com.lti.flipfit.repository;

/**
 * Author :
 * Version : 1.0
 * Description : JPA repository for Gym Owner operations.
 */

import com.lti.flipfit.entity.GymOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlipFitGymOwnerRepository extends JpaRepository<GymOwner, String> {
}
