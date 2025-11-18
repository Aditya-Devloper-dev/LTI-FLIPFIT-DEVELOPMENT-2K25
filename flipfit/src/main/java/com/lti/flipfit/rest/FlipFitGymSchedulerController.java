package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymSchedulerService;
import org.springframework.web.bind.annotation.*;

/**
 * Author      :
 * Version     : 1.0
 * Description : Controller for scheduler operations triggered manually for testing.
 */
@RestController
@RequestMapping("/scheduler")
public class FlipFitGymSchedulerController {

    private final FlipFitGymSchedulerService service;

    public FlipFitGymSchedulerController(FlipFitGymSchedulerService service) {
        this.service = service;
    }

    /*
     * @Method: Triggering the waitlist promotion job
     * @Description: Manually runs the job that promotes users from the waitlist when slots free up
     * @MethodParameters: None
     * @Exception: Throws exceptions if job execution fails
     */

    @PostMapping("/run-waitlist-job")
    public String runWaitlistPromotionJob() {
        service.runWaitlistPromotionJob();
        return "Waitlist promotion job executed.";
    }

    /*
     * @Method: Sending daily reminder notifications
     * @Description: Manually triggers the scheduler to send daily reminders to users
     * @MethodParameters: None
     * @Exception: Throws exceptions if reminder execution fails
     */

    @PostMapping("/send-reminders")
    public String sendDailyReminders() {
        service.sendDailyReminders();
        return "Daily reminders sent.";
    }
}
