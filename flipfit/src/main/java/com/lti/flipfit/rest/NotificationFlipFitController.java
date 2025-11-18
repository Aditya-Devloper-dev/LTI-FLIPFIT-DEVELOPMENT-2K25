package com.lti.flipfit.rest;

import com.lti.flipfit.services.NotificationFlipFitService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for sending notifications.
 */
@RestController
@RequestMapping("/notification")
public class NotificationFlipFitController {

    private final NotificationFlipFitService service;

    public NotificationFlipFitController(NotificationFlipFitService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public boolean sendNotification(@RequestParam String receiverId,
                                    @RequestParam String message,
                                    @RequestParam String type) {

        return service.sendNotification(receiverId, message, type);
    }
}
