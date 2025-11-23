package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for scheduler operations triggered manually for
 * testing.
 */
@RestController
@RequestMapping("/scheduler")
public class FlipFitGymSchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymSchedulerController.class);

    private final FlipFitGymSchedulerService service;

    public FlipFitGymSchedulerController(FlipFitGymSchedulerService service) {
        this.service = service;
    }

    /*
     * @Method: Triggering the waitlist promotion job
     * 
     * @Description: Manually runs the job that promotes users from the waitlist
     * when slots free up
     * 
     * @MethodParameters: None
     * 
     * @Exception: Throws exceptions if job execution fails
     */

    @RequestMapping(value = "/run-waitlist-job", method = RequestMethod.POST)
    public String runWaitlistPromotionJob() {
        logger.info("Received request to manually run waitlist promotion job");
        service.runWaitlistPromotionJob();
        return "Waitlist promotion job executed.";
    }

    /*
     * @Method: Sending daily reminder notifications
     * 
     * @Description: Manually triggers the scheduler to send daily reminders to
     * users
     * 
     * @MethodParameters: None
     * 
     * @Exception: Throws exceptions if reminder execution fails
     */

    @RequestMapping(value = "/send-reminders", method = RequestMethod.POST)
    public String sendDailyReminders() {
        logger.info("Received request to manually send daily reminders");
        service.sendDailyReminders();
        return "Daily reminders sent.";
    }
}
