package com.lti.flipfit.repository;

import com.lti.flipfit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author :
 * Version : 1.0
 * Description : Repository interface for Role entity.
 */
@Repository
public interface FlipFitGymRoleRepository extends JpaRepository<Role, String> {
}
