package com.lti.flipfit.rest;

import com.lti.flipfit.services.UserFlipFitService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserFlipFitController {

    private final UserFlipFitService service;

    public UserFlipFitController(UserFlipFitService service) {
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
