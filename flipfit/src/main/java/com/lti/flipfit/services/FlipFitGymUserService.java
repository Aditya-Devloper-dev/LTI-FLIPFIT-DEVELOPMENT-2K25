package com.lti.flipfit.services;

import com.lti.flipfit.beans.User;

import java.util.List;
import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles user account actions such as registration and login.
 */
public interface FlipFitGymUserService {

    String register(User user);

    Map<String, Object> login(String email, String password);

    boolean updateProfile(String userId);

    List<User> getAllUsers();


}
