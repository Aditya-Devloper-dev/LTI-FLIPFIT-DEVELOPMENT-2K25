package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.exceptions.user.*;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.repository.FlipFitGymAdminRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymOwnerRepository;
import com.lti.flipfit.repository.FlipFitGymUserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.lti.flipfit.constants.RoleType;

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
    private FlipFitGymUserRepository userRepo;

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

    @Autowired
    private FlipFitGymAdminRepository adminRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String register(User user) {
        logger.info("Attempting to register user with email: {}", user.getEmail());

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new InvalidInputException("Full name is required");
        }

        validateEmail(user.getEmail());

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidInputException("Password is required");
        }

        if (userRepo.existsByEmailIgnoreCase(user.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + user.getEmail());
        }

        if (userRepo.existsByEmailIgnoreCaseAndPhoneNumber(
                user.getEmail(), user.getPhoneNumber())) {
            throw new UserAlreadyExistsException("User already exists with this email and phone");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // createdAt & updatedAt handled by @PrePersist / @PreUpdate

        userRepo.save(user);

        if (RoleType.ADMIN.name().equals(user.getRole().getRoleId())) {
            GymAdmin admin = new GymAdmin();
            admin.setUser(user);
            adminRepo.save(admin);
        }
        if (RoleType.CUSTOMER.name().equals(user.getRole().getRoleId())) {
            GymCustomer customer = new GymCustomer();
            customer.setUser(user);
            customerRepo.save(customer);
        }
        if (RoleType.OWNER.name().equals(user.getRole().getRoleId())) {
            GymOwner owner = new GymOwner();
            owner.setUser(user);
            owner.setApproved(false);
            ownerRepo.save(owner);
        }

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

        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Email cannot be empty");
        }

        if (password == null || password.isBlank()) {
            throw new InvalidInputException("Password cannot be empty");
        }

        User foundUser = Optional.ofNullable(userRepo.findByEmailIgnoreCase(email))
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
    public String updateProfile(Long userId, User updatedData) {
        logger.info("Updating profile for user ID: {}", userId);

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
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepo.findAll();
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Email is required");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("Invalid email format");
        }
    }
}
