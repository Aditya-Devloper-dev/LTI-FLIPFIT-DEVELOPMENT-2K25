package com.lti.flipfit.entity;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Role.
 */

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gymrole")
@Data
public class Role {

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    // Stored as comma-separated string
    @Column(name = "permissions")
    private String permissions;
}
