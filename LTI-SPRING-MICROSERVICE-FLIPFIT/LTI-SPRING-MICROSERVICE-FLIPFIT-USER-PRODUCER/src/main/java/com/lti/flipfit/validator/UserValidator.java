package com.lti.flipfit.validator;

/**
 * Author :
 * Version : 1.0
 * Description : Validator class for User operations. Handles validation logic for registration, login, and profile updates.
 */

import com.lti.flipfit.dao.FlipFitGymUserDAO;
import com.lti.flipfit.entity.User;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.user.DuplicateEmailException;
import com.lti.flipfit.exceptions.user.UserAlreadyExistsException;

import com.lti.flipfit.repository.FlipFitGymRoleRepository;
import com.lti.flipfit.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired
    private FlipFitGymUserDAO userDAO;

    @Autowired
    private FlipFitGymRoleRepository roleRepo;

    /**
     * @methodname - validateRegistration
     * @description - Validates user registration details including email format,
     *              mandatory fields, and duplicate checks.
     * @param - user The user object to validate.
     * @throws InvalidInputException      if input is invalid.
     * @throws DuplicateEmailException    if email already exists.
     * @throws UserAlreadyExistsException if user with same email and phone exists.
     */
    public void validateRegistration(User user) {
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new InvalidInputException("Full name is required");
        }

        ValidationUtils.validateEmail(user.getEmail());

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new InvalidInputException("Password is required");
        }

        if (userDAO.checkUserExists(user.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + user.getEmail());
        }

        if (userDAO.checkUserExistsByEmailAndPhone(user.getEmail(), user.getPhoneNumber())) {
            throw new UserAlreadyExistsException("User already exists with this email and phone");
        }

        if (user.getRole() == null || user.getRole().getRoleId() == null) {
            throw new InvalidInputException("Role information is missing");
        }

        // Check if role exists
        roleRepo.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new InvalidInputException("Invalid Role ID: " + user.getRole().getRoleId()));
    }

    /**
     * @methodname - validateLogin
     * @description - Validates login credentials.
     * @param - email The user's email.
     * @param - password The user's password.
     * @throws InvalidInputException if email or password is empty.
     */
    public void validateLogin(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Email cannot be empty");
        }

        if (password == null || password.isBlank()) {
            throw new InvalidInputException("Password cannot be empty");
        }
    }

    /**
     * @methodname - validateUpdateProfile
     * @description - Validates user profile update request.
     * @param - userId The ID of the user to update.
     * @param - updatedData The new user data.
     * @throws InvalidInputException if userId is null.
     */
    public void validateUpdateProfile(Long userId, User updatedData) {
        if (userId == null) {
            throw new InvalidInputException("User ID cannot be null");
        }
        // Additional validations for updatedData if needed
    }
}
