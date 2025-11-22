package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author : <Your Name>
 * Version : 1.0
 * Repository : GymCenterRepository
 * Description : JPA repository for managing CRUD operations on GymCenter.
 * Includes custom query method to detect duplicate centers.
 */

@Repository
public interface FlipFitGymCenterRepository extends JpaRepository<GymCenter, Long> {

    boolean existsByCenterNameIgnoreCaseAndCityIgnoreCase(String name, String city);
}
