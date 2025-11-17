package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Handles user account actions such as registration and login.
 */
public interface UserFlipFitService {

    String register(Object userDto);

    Object login(String email, String password);

    boolean updateProfile(String userId);

}
