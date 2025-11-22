package com.lti.flipfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LtiSpringRestFlipfitApiJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringRestFlipfitApiJpaApplication.class, args);
	}

}
