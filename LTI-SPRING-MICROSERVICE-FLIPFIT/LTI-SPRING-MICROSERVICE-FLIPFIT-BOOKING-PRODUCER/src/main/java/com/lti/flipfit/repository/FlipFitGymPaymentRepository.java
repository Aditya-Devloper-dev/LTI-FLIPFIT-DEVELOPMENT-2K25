package com.lti.flipfit.repository;

import com.lti.flipfit.entity.GymPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author :
 * Version : 1.0
 * Description : Repository interface for GymPayment entity.
 */
@Repository
public interface FlipFitGymPaymentRepository extends JpaRepository<GymPayment, String> {

}
