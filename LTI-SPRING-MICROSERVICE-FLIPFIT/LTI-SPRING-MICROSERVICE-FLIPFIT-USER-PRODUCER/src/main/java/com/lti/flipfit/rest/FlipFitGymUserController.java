package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymUserService;
import com.lti.flipfit.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for user registration, login, and profile updates
 * across admin, owner, and customer roles. Includes validation and exception
 * handling using the central exception framework.
 */
@RestController
@RequestMapping("/user")
public class FlipFitGymUserController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymUserController.class);

    private final FlipFitGymUserService service;

    public FlipFitGymUserController(FlipFitGymUserService service) {
        this.service = service;
    }

    /**
     * @methodname - register
     * @description - Registers a new user (admin/owner/customer) into the system.
     * @param - user The user object containing registration details.
     * @return - A success message with the registered user ID.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        logger.info("Received request to register user with email: {}", user.getEmail());
        return service.register(user);
    }

    /**
     * @methodname - login
     * @description - Authenticates user using email and password.
     * @param - email The user's email address.
     * @param - password The user's password.
     * @return - A map containing user details and login status.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String email,
            @RequestParam String password) {
        logger.info("Received request to login user with email: {}", email);
        return service.login(email, password);
    }

    /**
     * @methodname - updateProfile
     * @description - Updates user profile information for a given user ID.
     * @param - userId The unique identifier of the user.
     * @param - userData The updated user data.
     * @return - A success message confirming the profile update.
     */
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
    public String updateProfile(
            @PathVariable Long userId,
            @RequestBody User userData) {
        logger.info("Received request to update profile for user ID: {}", userId);
        return service.updateProfile(userId, userData);
    }

    /**
     * @methodname - getAllUsers
     * @description - Returns a list of all registered users in the system.
     * @return - A list of all users.
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        logger.info("Received request to get all users");
        return service.getAllUsers();
    }
}
