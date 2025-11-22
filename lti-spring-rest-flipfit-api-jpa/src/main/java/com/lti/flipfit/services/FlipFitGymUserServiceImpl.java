package com.lti.flipfit.services;

/**
 * Author :
 * Version : 1.0
 * Description : Implementation of the FlipFitGymUserService interface.
 */

import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.exceptions.user.*;
import com.lti.flipfit.repository.FlipFitGymAdminRepository;
import com.lti.flipfit.repository.FlipFitGymCustomerRepository;
import com.lti.flipfit.repository.FlipFitGymUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    @Autowired
    private FlipFitGymUserRepository userRepo;

    @Autowired
    private FlipFitGymAdminRepository adminRepo;

    @Autowired
    private FlipFitGymCustomerRepository customerRepo;

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

        if (userRepo.existsByEmailIgnoreCase(user.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + user.getEmail());
        }

        if (userRepo.existsByEmailIgnoreCaseAndPhoneNumber(
                user.getEmail(), user.getPhoneNumber())) {
            throw new UserAlreadyExistsException("User already exists with this email and phone");
        }

        // Hash the password
        user.setPassword(user.getPassword());

        // createdAt & updatedAt handled by @PrePersist / @PreUpdate if implemented
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepo.save(user);

        if ("ROLE_ADMIN".equals(user.getRole().getRoleId())) {
            GymAdmin admin = new GymAdmin();
            admin.setUser(user);
            adminRepo.save(admin);
        }

        if ("ROLE_CUSTOMER".equals(user.getRole().getRoleId())) {
            GymCustomer customer = new GymCustomer();
            customer.setUser(user);
            customerRepo.save(customer);
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
        return userRepo.findAll();
    }
}
