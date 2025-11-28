package com.lti.flipfit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Author :
 * Version : 1.0
 * Description : Entity class representing Role.
 */
@Entity
@Table(name = "gymrole")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    // Stored as comma-separated string
    @Column(name = "permissions")
    private String permissions;
}
