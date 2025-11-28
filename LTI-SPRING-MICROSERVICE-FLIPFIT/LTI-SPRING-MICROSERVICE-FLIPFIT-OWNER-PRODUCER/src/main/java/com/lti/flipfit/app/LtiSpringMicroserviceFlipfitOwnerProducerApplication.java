package com.lti.flipfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lti.flipfit")
@EntityScan("com.lti.flipfit.entity")
@EnableJpaRepositories("com.lti.flipfit.repository")
@EnableCaching
public class LtiSpringMicroserviceFlipfitOwnerProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceFlipfitOwnerProducerApplication.class, args);
	}

}
