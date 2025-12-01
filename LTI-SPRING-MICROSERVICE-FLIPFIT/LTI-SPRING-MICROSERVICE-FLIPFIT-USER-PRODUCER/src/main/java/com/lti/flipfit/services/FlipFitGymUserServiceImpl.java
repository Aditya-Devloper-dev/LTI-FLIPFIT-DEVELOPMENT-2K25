package com.lti.flipfit.services;

import com.lti.flipfit.dao.FlipFitGymUserDAO;
import com.lti.flipfit.entity.*;
import com.lti.flipfit.exceptions.user.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.repository.FlipFitGymRoleRepository;
import com.lti.flipfit.repository.FlipFitGymUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.cache.annotation.*;
import com.lti.flipfit.utils.UserNotificationHelper;
import com.lti.flipfit.utils.UserRoleHelper;
import com.lti.flipfit.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Author :
 * Version : 1.0
 * Description : Service implementation for user account management including
 * registration, authentication, and profile updates. Handles validation,
 * role-based user
 * creation, and exception handling for all user operations.
 */

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymUserServiceImpl.class);

    @Autowired
    private FlipFitGymUserDAO userDAO;

    @Autowired
    private FlipFitGymUserRepository userRepo;

    @Autowired
    private FlipFitGymRoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRoleHelper userRoleHelper;

    @Autowired
    private UserNotificationHelper userNotificationHelper;

    /**
     * @methodname - register
     * @description - Registers a new user in the system after validating email and
     *              duplicate accounts.
     *              Creates role-specific entries (Admin, Customer, or Owner) based
     *              on the user's role.
     * @param - user The user object containing registration details.
     * @return - A success message with the registered user ID.
     * @throws InvalidInputException      if required fields are missing or invalid.
     * @throws DuplicateEmailException    if the email is already registered.
     * @throws UserAlreadyExistsException if a user with the same email and phone
     *                                    exists.
     */
    @Override
    @Transactional
    @CacheEvict(value = "allUsers", allEntries = true)
    public String register(User user) {
        logger.info("Attempting to register user with email: {}", user.getEmail());

        userValidator.validateRegistration(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findById(user.getRole().getRoleId()).get();
        user.setRole(role);

        userRepo.save(user);

        userRoleHelper.createUserRoleEntity(user);

        userNotificationHelper.sendWelcomeNotification(user);

        return "User registered with ID: " + user.getUserId();
    }

    /**
     * @methodname - login
     * @description - Authenticates a user based on email and password.
     * @param - email The user's email address.
     * @param - password The user's password.
     * @return - A map containing user details (userId, email, roleId) and login
     *         status.
     * @throws InvalidInputException         if email or password is empty.
     * @throws UserNotFoundException         if no user is found with the given
     *                                       email.
     * @throws AuthenticationFailedException if the password is incorrect.
     */
    @Override
    public Map<String, Object> login(String email, String password) {
        logger.info("Attempting login for email: {}", email);

        userValidator.validateLogin(email, password);

        User foundUser = Optional.ofNullable(userDAO.findUserByEmail(email))
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new AuthenticationFailedException("Incorrect email or password");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", foundUser.getUserId());
        response.put("email", foundUser.getEmail());
        response.put("roleId", foundUser.getRole().getRoleId());
        response.put("loginStatus", "SUCCESS");

        return response;
    }

    /**
     * @methodname - updateProfile
     * @description - Updates user profile information for the given user ID.
     * @param - userId The unique identifier of the user.
     * @param - updatedData The updated user data containing fields to be modified.
     * @return - A success message confirming the profile update.
     * @throws UserNotFoundException if no user is found with the given ID.
     */
    @Override
    @Transactional
    @CacheEvict(value = "allUsers", allEntries = true)
    public String updateProfile(Long userId, User updatedData) {
        logger.info("Updating profile for user ID: {}", userId);

        userValidator.validateUpdateProfile(userId, updatedData);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        if (updatedData.getFullName() != null) {
            user.setFullName(updatedData.getFullName());
        }

        if (updatedData.getPhoneNumber() != null) {
            user.setPhoneNumber(updatedData.getPhoneNumber());
        }

        if (updatedData.getPassword() != null && !updatedData.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedData.getPassword()));
        }

        if (updatedData.getRole() != null) {
            user.setRole(updatedData.getRole());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);

        return "User has been updated successfully with userId: " + userId;
    }

    /**
     * @methodname - getAllUsers
     * @description - Retrieves all registered users in the system.
     * @return - A list of all users.
     */
    @Override
    @Cacheable(value = "allUsers")
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepo.findAll();
    }
}
