package com.lti.flipfit.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String role; // "CUSTOMER" or "GYM_OWNER" or "ADMIN"

    // Gym Owner specific fields
    private String businessName;
    private String gstNumber;
    private String panNumber;
}
