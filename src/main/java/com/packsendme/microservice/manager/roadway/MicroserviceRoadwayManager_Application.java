package com.packsendme.microservice.manager.roadway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceRoadwayManager_Application {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceRoadwayManager_Application.class, args);
	}
}

