package com.noir.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableEurekaServer
@SpringBootApplication
public class JobPortalApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApiGatewayApplication.class, args);
	}

}
