package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FlipFitGymUserServiceImpl implements FlipFitGymUserService {

    private final Map<String, Map<String, Object>> userStore = new HashMap<>();

    @Override
    public String register(Object userDto) {

        String userId = UUID.randomUUID().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("userId", userId);
        user.put("data", userDto);

        userStore.put(userId, user);

        return "User registered with ID: " + userId;
    }

    @Override
    public Map<String, Object> login(String email, String password) {

        // Dummy login response
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("token", UUID.randomUUID().toString());
        response.put("loginStatus", "SUCCESS");

        return response;
    }

    @Override
    public boolean updateProfile(String userId) {
        return userStore.containsKey(userId); // true if exists
    }
}
