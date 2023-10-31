package com.bksproject.bksproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BksProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BksProjectApplication.class, args);
	}

}
