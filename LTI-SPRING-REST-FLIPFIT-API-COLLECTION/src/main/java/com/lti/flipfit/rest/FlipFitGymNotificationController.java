package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymNotificationService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for sending notifications.
 */
@RestController
@RequestMapping("/notification")
public class FlipFitGymNotificationController {

    private final FlipFitGymNotificationService service;

    public FlipFitGymNotificationController(FlipFitGymNotificationService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public boolean sendNotification(@RequestParam String receiverId,
                                    @RequestParam String message,
                                    @RequestParam String type) {

        return service.sendNotification(receiverId, message, type);
    }
}
