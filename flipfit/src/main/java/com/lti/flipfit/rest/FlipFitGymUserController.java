package com.lti.flipfit.rest;

import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.UserNotFoundException;
import com.lti.flipfit.services.FlipFitGymUserService;
import com.lti.flipfit.beans.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for user registration, login, and profile updates across
 *               admin, owner, and customer roles. Includes validation and exception
 *               handling using the central exception framework.
 */
@RestController
@RequestMapping("/user")
public class FlipFitGymUserController {

    private final FlipFitGymUserService service;

    public FlipFitGymUserController(FlipFitGymUserService service) {
        this.service = service;
    }

    /*
     * @Method: register
     * @Description: Registers a new user (admin/owner/customer) into the system.
     * @MethodParameters: userDto -> JSON payload containing user registration data.
     * @Exception: Throws InvalidInputException for null payload or invalid registration.
     */
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new InvalidInputException("Full name is required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidInputException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidInputException("Password is required");
        }

        return service.register(user);
    }

    /*
     * @Method: login
     * @Description: Authenticates user using email and password.
     * @MethodParameters: email -> user's email, password -> user's password.
     * @Exception: Throws InvalidInputException for missing inputs,
     *             Throws UserNotFoundException if credentials are invalid.
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String email,
                                     @RequestParam String password) {

        if (email.isBlank()) {
            throw new InvalidInputException("Email cannot be empty");
        }

        if (password.isBlank()) {
            throw new InvalidInputException("Password cannot be empty");
        }

        // service.login() will throw if invalid
        return service.login(email, password);
    }

    /*
     * @Method: updateProfile
     * @Description: Updates user profile information for a given user ID.
     * @MethodParameters: userId -> Unique identifier of the user.
     * @Exception: Throws InvalidInputException for empty userId,
     *             Throws UserNotFoundException if user does not exist.
     */
    @PutMapping("/update/{userId}")
    public boolean updateProfile(@PathVariable String userId) {

        if (userId.isBlank()) {
            throw new InvalidInputException("User ID cannot be empty");
        }

        return service.updateProfile(userId); // throws if user not found
    }

    /*
     * @Method: getAllUsers
     * @Description: Returns a list of all registered users in the system.
     * @MethodParameters: None
     * @Exception: None (unless storage access fails)
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}
