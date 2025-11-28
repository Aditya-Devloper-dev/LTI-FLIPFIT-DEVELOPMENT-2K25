package com.lti.flipfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Author :
 * Version : 1.0
 * Description : Main application class for User Microservice.
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lti.flipfit")
@EntityScan("com.lti.flipfit.entity")
@EnableJpaRepositories("com.lti.flipfit.repository")
@EnableCaching
public class LtiSpringMicroserviceFlipfitUserProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceFlipfitUserProducerApplication.class, args);
	}

}
