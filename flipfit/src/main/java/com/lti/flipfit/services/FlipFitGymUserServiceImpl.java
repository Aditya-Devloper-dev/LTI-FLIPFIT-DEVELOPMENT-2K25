package com.lti.flipfit.services;

import com.lti.flipfit.beans.User;
import com.lti.flipfit.exceptions.InvalidInputException;
import com.lti.flipfit.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    private final Map<String, User> userStore = new HashMap<>();

    @Override
    public String register(User user) {

        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        userStore.put(userId, user); // Now this matches the Map type

        return "User registered with ID: " + userId;
    }


    @Override
    public Map<String, Object> login(String email, String password) {

        // Find user by email
        User foundUser = userStore.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

        // Check password
        if (!password.equals(foundUser.getPassword())) {
            throw new InvalidInputException("Incorrect password");
        }

        // Build login response
        Map<String, Object> response = new HashMap<>();
        response.put("userId", foundUser.getUserId());
        response.put("email", foundUser.getEmail());
        response.put("roleId", foundUser.getRoleId());
        response.put("token", UUID.randomUUID().toString());
        response.put("loginStatus", "SUCCESS");

        return response;
    }


    @Override
    public boolean updateProfile(String userId) {

        User user = userStore.get(userId);

        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        // Example update: refresh updatedAt timestamp
        user.setUpdatedAt(LocalDateTime.now());

        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }


}
