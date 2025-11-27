package com.lti.flipfit.rest;

import com.lti.flipfit.services.FlipFitGymSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

/**
 * Author :
 * Version : 1.0
 * Description : Controller for scheduler operations triggered manually for
 * testing.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lti.flipfit")
@EntityScan("com.lti.flipfit.entity")
@EnableJpaRepositories("com.lti.flipfit.repository")
public class FlipFitGymSchedulerController {

    private static final Logger logger = LoggerFactory.getLogger(FlipFitGymSchedulerController.class);

    private final FlipFitGymSchedulerService service;

    public FlipFitGymSchedulerController(FlipFitGymSchedulerService service) {
        this.service = service;
    }

    /**
     * @methodname - runWaitlistPromotionJob
     * @description - Manually runs the job that promotes users from the waitlist
     *              when slots free up.
     * @return - A success message.
     */
    @RequestMapping(value = "/run-waitlist-job", method = RequestMethod.POST)
    public String runWaitlistPromotionJob() {
        logger.info("Received request to manually run waitlist promotion job");
        service.runWaitlistPromotionJob();
        return "Waitlist promotion job executed.";
    }

    /**
     * @methodname - sendDailyReminders
     * @description - Manually triggers the scheduler to send daily reminders to
     *              users.
     * @return - A success message.
     */
    @RequestMapping(value = "/send-reminders", method = RequestMethod.POST)
    public String sendDailyReminders() {
        logger.info("Received request to manually send daily reminders");
        service.sendDailyReminders();
        return "Daily reminders sent.";
    }
}
