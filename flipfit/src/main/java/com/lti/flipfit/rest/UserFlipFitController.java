package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for user registration, login, and profile updates.
 */
@RestController
@RequestMapping("/user")
public class UserFlipFitController {

    @PostMapping("/register")
    public String register(@RequestBody Object userDto) {
        return null;
    }

    @PostMapping("/login")
    public Object login(@RequestParam String email,
                        @RequestParam String password) {
        return null;
    }

    @PutMapping("/update/{userId}")
    public boolean updateProfile(@PathVariable String userId) {
        return false;
    }
}
