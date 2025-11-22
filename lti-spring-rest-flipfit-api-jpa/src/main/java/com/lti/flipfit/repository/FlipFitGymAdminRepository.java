package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author : <Your Name>
 * Version : 1.0
 * Repository : GymAdminRepository
 * Description : JPA repository for managing GymAdmin records.
 */

@Repository
public interface FlipFitGymAdminRepository extends JpaRepository<GymAdmin, Long> {

}
