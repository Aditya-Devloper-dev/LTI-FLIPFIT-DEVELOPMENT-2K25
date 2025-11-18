package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymUserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for user registration, login, and profile updates across
 *               admin, owner, and customer roles.
 */

@RestController
@RequestMapping("/user")
public class FlipFitGymUserController {

    private final FlipFitGymUserService service;

    public FlipFitGymUserController(FlipFitGymUserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody Object userDto) {
        return service.register(userDto);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String email,
                                     @RequestParam String password) {
        return service.login(email, password);
    }

    @PutMapping("/update/{userId}")
    public boolean updateProfile(@PathVariable String userId) {
        return service.updateProfile(userId);
    }
}
