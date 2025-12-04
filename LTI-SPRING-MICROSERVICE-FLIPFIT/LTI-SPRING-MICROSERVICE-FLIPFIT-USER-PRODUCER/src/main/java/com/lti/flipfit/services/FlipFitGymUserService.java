package com.lti.flipfit.services;

import com.lti.flipfit.dto.UserRegistrationDTO;
import com.lti.flipfit.dto.UserLoginDTO;
import com.lti.flipfit.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Service interface for user account management including
 * registration, authentication, and profile updates across all user roles.
 */
public interface FlipFitGymUserService {

    /**
     * @methodname - register
     * @description - Registers a new user in the system.
     * @param - user The user object containing registration details.
     * @return - A success message with the registered user ID.
     */
    String register(UserRegistrationDTO userDto);

    /**
     * @methodname - login
     * @description - Authenticates user using email and password.
     * @param - loginDTO The login credentials.
     * @return - A map containing user details and login status.
     */
    Map<String, Object> login(UserLoginDTO loginDTO);

    /**
     * @methodname - updateProfile
     * @description - Updates user profile information.
     * @param - userId The unique identifier of the user.
     * @param - userData The updated user data.
     * @return - A success message confirming the profile update.
     */
    String updateProfile(Long userId, User userData);

    /**
     * @methodname - getAllUsers
     * @description - Retrieves all registered users in the system.
     * @return - A list of all users.
     */
    List<User> getAllUsers();

    /**
     * @methodname - getUserById
     * @description - Retrieves a user by their unique ID.
     * @param - userId The unique identifier of the user.
     * @return - The User entity.
     */
    User getUserById(Long userId);
}
