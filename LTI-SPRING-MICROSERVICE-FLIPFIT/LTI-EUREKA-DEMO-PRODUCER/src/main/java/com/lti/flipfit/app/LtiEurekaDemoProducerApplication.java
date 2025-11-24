package com.lti.flipfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LtiEurekaDemoProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiEurekaDemoProducerApplication.class, args);
	}

}
