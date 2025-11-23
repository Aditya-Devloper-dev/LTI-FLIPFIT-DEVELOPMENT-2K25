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

    /*
     * @Method: register
     * 
     * @Description: Registers a new user (admin/owner/customer) into the system.
     * 
     * @MethodParameters: userDto -> JSON payload containing user registration data.
     * 
     * @Exception: Throws InvalidInputException for null payload or invalid
     * registration.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        logger.info("Received request to register user with email: {}", user.getEmail());
        return service.register(user);
    }

    /*
     * @Method: login
     * 
     * @Description: Authenticates user using email and password.
     * 
     * @MethodParameters: email -> user's email, password -> user's password.
     * 
     * @Exception: Throws InvalidInputException for missing inputs,
     * Throws UserNotFoundException if credentials are invalid.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String email,
            @RequestParam String password) {
        logger.info("Received request to login user with email: {}", email);
        return service.login(email, password);
    }

    /*
     * @Method: updateProfile
     * 
     * @Description: Updates user profile information for a given user ID.
     * 
     * @MethodParameters: userId -> Unique identifier of the user.
     * 
     * @Exception: Throws InvalidInputException for empty userId,
     * Throws UserNotFoundException if user does not exist.
     */
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
    public String updateProfile(
            @PathVariable Long userId,
            @RequestBody User userData) {
        logger.info("Received request to update profile for user ID: {}", userId);
        return service.updateProfile(userId, userData);
    }

    /*
     * @Method: getAllUsers
     * 
     * @Description: Returns a list of all registered users in the system.
     * 
     * @MethodParameters: None
     * 
     * @Exception: None (unless storage access fails)
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        logger.info("Received request to get all users");
        return service.getAllUsers();
    }
}
