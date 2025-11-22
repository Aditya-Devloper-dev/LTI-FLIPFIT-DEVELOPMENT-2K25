package com.lti.flipfit;

/**
 * Author :
 * Version : 1.0
 * Description : Main Spring Boot Application class.
 */

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
