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

import java.time.LocalDateTime;
import java.util.*;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymUserService interface.
 */

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymUserServiceImpl.class);

    @Autowired
    private FlipFitGymUserRepository userRepo;

    @Autowired
    private FlipFitGymAdminRepository adminRepo;

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

    @Autowired
    private FlipFitGymOwnerRepository ownerRepo;

    /*
     * @Method: register
     * 
     * @Description: Registers a new user after validating email and duplicate
     * accounts.
     * 
     * @MethodParameters: user -> User object received from request
     * 
     * @Exception: Throws DuplicateEmailException, UserAlreadyExistsException
     */
    @Override
    @Transactional
    public String register(User user) {
        logger.info("Attempting to register user with email: {}", user.getEmail());

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new InvalidInputException("Full name is required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidInputException("Email is required");
        }

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
        user.setPassword(user.getPassword());
        // createdAt & updatedAt handled by @PrePersist / @PreUpdate

        userRepo.save(user);

        if ("ADMIN".equals(user.getRole().getRoleId())) {
            GymAdmin admin = new GymAdmin();
            admin.setUser(user);
            adminRepo.save(admin);
        }

        if ("CUSTOMER".equals(user.getRole().getRoleId())) {
            GymCustomer customer = new GymCustomer();
            customer.setUser(user);
            customerRepo.save(customer);
        }

        if ("OWNER".equals(user.getRole().getRoleId())) {
            GymOwner owner = new GymOwner();
            owner.setUser(user);
            owner.setApproved(false);
            // businessName, gstNumber, panNumber are null initially
            ownerRepo.save(owner);
        }

        return "User registered with ID: " + user.getUserId();
    }

    /*
     * @Method: login
     * 
     * @Description: Authenticates a user based on email and password.
     * 
     * @MethodParameters: email, password
     * 
     * @Exception: Throws UserNotFoundException, AuthenticationFailedException
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

        // Plain text comparison (temporary)
        if (!foundUser.getPassword().equals(password)) {
            throw new AuthenticationFailedException("Incorrect email or password");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", foundUser.getUserId());
        response.put("email", foundUser.getEmail());
        response.put("roleId", foundUser.getRole().getRoleId());
        response.put("loginStatus", "SUCCESS");

        return response;
    }

    /*
     * @Method: updateProfile
     * 
     * @Description: Updates user details for the given userId.
     * 
     * @MethodParameters: userId -> unique user identifier
     * 
     * @Exception: Throws UserNotFoundException if the user is missing
     */
    @Override
    @Transactional
    public String updateProfile(Long userId, User updatedData) {
        logger.info("Updating profile for user ID: {}", userId);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        if (updatedData.getFullName() != null)
            user.setFullName(updatedData.getFullName());

        if (updatedData.getPhoneNumber() != null)
            user.setPhoneNumber(updatedData.getPhoneNumber());

        if (updatedData.getPassword() != null)
            user.setPassword(updatedData.getPassword());

        if (updatedData.getRole() != null)
            user.setRole(updatedData.getRole());

        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);

        return "User has been updated successfully with userId: " + userId;
    }

    /*
     * @Method: getAllUsers
     * 
     * @Description: Returns a list of all registered users.
     * 
     * @MethodParameters: None
     * 
     * @Exception: None
     */
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepo.findAll();
    }
}
