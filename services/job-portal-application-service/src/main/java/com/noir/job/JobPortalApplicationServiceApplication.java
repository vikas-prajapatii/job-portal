package com.noir.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class JobPortalApplicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplicationServiceApplication.class, args);
	}
}
