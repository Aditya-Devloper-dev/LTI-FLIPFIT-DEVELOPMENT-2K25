package com.lti.flipfit.client;

import com.lti.flipfit.entity.GymPayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "LTI-SPRING-MICROSERVICE-FLIPFIT-BOOKING-PRODUCER")
public interface FlipFitGymBookingClient {

    @GetMapping("/booking/payments")
    List<GymPayment> viewPayments(
            @RequestParam(value = "filterType", defaultValue = "ALL") String filterType,
            @RequestParam(value = "date", required = false) String date);
}
