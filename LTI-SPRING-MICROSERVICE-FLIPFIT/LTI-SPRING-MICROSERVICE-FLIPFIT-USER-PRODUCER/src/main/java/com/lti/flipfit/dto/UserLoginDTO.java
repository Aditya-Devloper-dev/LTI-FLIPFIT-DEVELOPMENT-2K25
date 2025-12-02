package com.lti.flipfit.dto;

import lombok.Data;

/**
 * Author :
 * Version : 1.0
 * Description : DTO for user login credentials.
 */
@Data
public class UserLoginDTO {
    private String email;
    private String password;
}
