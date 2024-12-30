package com.bootcamp.customermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomermanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomermanagementApplication.class, args);
	}

}
