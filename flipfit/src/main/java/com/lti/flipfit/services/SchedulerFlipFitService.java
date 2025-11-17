package com.lti.flipfit.services;

/**
 * Author      :
 * Version     : 1.0
 * Description : Automated tasks such as reminders and waitlist promotions.
 */
public interface SchedulerFlipFitService {

    void runWaitlistPromotionJob();

    void sendDailyReminders();

}
