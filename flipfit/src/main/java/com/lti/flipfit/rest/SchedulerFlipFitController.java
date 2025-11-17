package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for scheduler operations triggered manually for testing.
 */
@RestController
@RequestMapping("/scheduler")
public class SchedulerFlipFitController {

    @PostMapping("/run-waitlist-job")
    public void runWaitlistPromotionJob() {}

    @PostMapping("/send-reminders")
    public void sendDailyReminders() {}
}
