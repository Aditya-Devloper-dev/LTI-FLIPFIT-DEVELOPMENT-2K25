package com.lti.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableDiscoveryClient
public class SpringcloudApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudApiGatewayApplication.class, args);
	}

}
