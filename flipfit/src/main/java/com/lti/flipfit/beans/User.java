package com.lti.flipfit.beans;

/**
 * Author      :
 * Version     : 1.0
 * Description : Base entity representing a system user.
 *               Extended by GymAdmin, GymOwner, GymCustomer.
 */
public class User {

    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String roleId;
    private String createdAt;
    private String updatedAt;

}
