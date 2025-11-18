package com.lti.flipfit.services;

import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles user account actions such as registration and login.
 */
public interface FlipFitGymUserService {

    String register(Object userDto);

    Map<String, Object> login(String email, String password);

    boolean updateProfile(String userId);

}
