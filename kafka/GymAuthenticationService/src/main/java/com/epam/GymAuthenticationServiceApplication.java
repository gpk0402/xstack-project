package com.epam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
//@EnableDiscoveryClient
@OpenAPIDefinition
public class GymAuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymAuthenticationServiceApplication.class, args);
	}

}
