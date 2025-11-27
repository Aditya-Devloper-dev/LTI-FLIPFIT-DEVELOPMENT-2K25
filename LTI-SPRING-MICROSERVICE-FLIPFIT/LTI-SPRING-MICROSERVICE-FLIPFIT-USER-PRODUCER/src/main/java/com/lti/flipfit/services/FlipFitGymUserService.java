package com.lti.flipfit.services;

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
    String register(User user);

    /**
     * @methodname - login
     * @description - Authenticates a user based on email and password.
     * @param - email The user's email address.
     * @param - password The user's password.
     * @return - A map containing user details and login status.
     */
    Map<String, Object> login(String email, String password);

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
}
