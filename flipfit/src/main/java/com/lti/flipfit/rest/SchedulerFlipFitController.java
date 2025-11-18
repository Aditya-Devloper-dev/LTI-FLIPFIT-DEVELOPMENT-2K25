package com.lti.flipfit.rest;

import com.lti.flipfit.services.SchedulerFlipFitService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for scheduler operations triggered manually for testing.
 */
@RestController
@RequestMapping("/scheduler")
public class SchedulerFlipFitController {

    private final SchedulerFlipFitService service;

    public SchedulerFlipFitController(SchedulerFlipFitService service) {
        this.service = service;
    }

    @PostMapping("/run-waitlist-job")
    public String runWaitlistPromotionJob() {
        service.runWaitlistPromotionJob();
        return "Waitlist promotion job executed.";
    }

    @PostMapping("/send-reminders")
    public String sendDailyReminders() {
        service.sendDailyReminders();
        return "Daily reminders sent.";
    }
}
