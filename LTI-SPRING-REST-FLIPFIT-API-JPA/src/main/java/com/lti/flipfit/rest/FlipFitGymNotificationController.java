package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for sending notifications.
 */
@RestController
@RequestMapping("/notification")
public class FlipFitGymNotificationController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymNotificationController.class);

    private final FlipFitGymNotificationService service;

    public FlipFitGymNotificationController(FlipFitGymNotificationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public boolean sendNotification(@RequestParam String receiverId,
            @RequestParam String message,
            @RequestParam String type) {
        logger.info("Received request to send notification to: {}, Type: {}", receiverId, type);

        return service.sendNotification(receiverId, message, type);
    }
}
