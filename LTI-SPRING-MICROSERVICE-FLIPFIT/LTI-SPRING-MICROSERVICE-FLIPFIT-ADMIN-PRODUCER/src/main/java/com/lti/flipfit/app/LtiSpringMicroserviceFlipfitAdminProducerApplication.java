package com.lti.flipfit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableDiscoveryClient
@org.springframework.cloud.openfeign.EnableFeignClients(basePackages = "com.lti.flipfit")
@ComponentScan("com.lti.flipfit")
@EntityScan("com.lti.flipfit.entity")
@EnableJpaRepositories("com.lti.flipfit.repository")
@EnableCaching
public class LtiSpringMicroserviceFlipfitAdminProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtiSpringMicroserviceFlipfitAdminProducerApplication.class, args);
	}

}
