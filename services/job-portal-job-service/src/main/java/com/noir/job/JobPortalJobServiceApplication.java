package com.noir.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JobPortalJobServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalJobServiceApplication.class, args);
	}

}
