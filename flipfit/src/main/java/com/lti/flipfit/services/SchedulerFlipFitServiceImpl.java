package com.lti.flipfit.services;

import org.springframework.stereotype.Service;

@Service
public class SchedulerFlipFitServiceImpl implements SchedulerFlipFitService {

    @Override
    public void runWaitlistPromotionJob() {
        System.out.println("Dummy: Waitlist promotion job executed.");
    }

    @Override
    public void sendDailyReminders() {
        System.out.println("Dummy: Daily reminder job executed.");
    }
}
