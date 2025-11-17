package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for sending notifications.
 */
@RestController
@RequestMapping("/notification")
public class NotificationFlipFitController {

    @PostMapping("/send")
    public boolean sendNotification(@RequestParam String receiverId,
                                    @RequestParam String message,
                                    @RequestParam String type) {
        return false;
    }
}
