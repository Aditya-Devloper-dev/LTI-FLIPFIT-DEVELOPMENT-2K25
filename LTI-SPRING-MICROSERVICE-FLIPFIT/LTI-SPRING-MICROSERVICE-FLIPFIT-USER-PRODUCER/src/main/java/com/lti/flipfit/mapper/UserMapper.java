package com.lti.flipfit.mapper;

import com.lti.flipfit.dto.UserRegistrationDTO;
import com.lti.flipfit.entity.User;
import org.springframework.stereotype.Component;

/**
 * Author :
 * Version : 1.0
 * Description : Mapper class to convert UserRegistrationDTO to User entity.
 */
@Component
public class UserMapper {

    /**
     * Converts UserRegistrationDTO to User entity.
     * 
     * @param dto The DTO to convert.
     * @return The User entity.
     */
    public User toEntity(UserRegistrationDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());

        // Role mapping is handled in the service layer as it requires repository access

        return user;
    }
}
