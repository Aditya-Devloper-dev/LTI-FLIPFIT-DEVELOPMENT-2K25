package com.lti.flipfit.services;

import com.lti.flipfit.beans.User;
import com.lti.flipfit.exceptions.user.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    private final Map<String, User> userStore = new HashMap<>();

    /*
     * @Method: register
     * @Description: Registers a new user after validating email and duplicate accounts.
     * @MethodParameters: user -> User object received from request
     * @Exception: Throws DuplicateEmailException, UserAlreadyExistsException
     */
    @Override
    public String register(User user) {

        boolean emailExists = userStore.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));
        if (emailExists) {
            throw new DuplicateEmailException("Email already registered: " + user.getEmail());
        }

        boolean userExists = userStore.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail())
                        && u.getPhoneNumber().equals(user.getPhoneNumber()));
        if (userExists) {
            throw new UserAlreadyExistsException("User already exists with this email and phone");
        }

        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userStore.put(userId, user);
        return "User registered with ID: " + userId;
    }

    /*
     * @Method: login
     * @Description: Authenticates a user based on email and password.
     * @MethodParameters: email, password
     * @Exception: Throws UserNotFoundException, AuthenticationFailedException
     */
    @Override
    public Map<String, Object> login(String email, String password) {

        User foundUser = userStore.values().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        if (!password.equals(foundUser.getPassword())) {
            throw new AuthenticationFailedException("Incorrect email or password");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", foundUser.getUserId());
        response.put("email", foundUser.getEmail());
        response.put("roleId", foundUser.getRoleId());
        response.put("token", UUID.randomUUID().toString());
        response.put("loginStatus", "SUCCESS");

        return response;
    }

    /*
     * @Method: updateProfile
     * @Description: Updates user details for the given userId.
     * @MethodParameters: userId -> unique user identifier
     * @Exception: Throws UserNotFoundException if the user is missing
     */
    @Override
    public boolean updateProfile(String userId) {

        User user = userStore.get(userId);

        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        user.setUpdatedAt(LocalDateTime.now());
        return true;
    }

    /*
     * @Method: getAllUsers
     * @Description: Returns a list of all registered users.
     * @MethodParameters: None
     * @Exception: None
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }
}
