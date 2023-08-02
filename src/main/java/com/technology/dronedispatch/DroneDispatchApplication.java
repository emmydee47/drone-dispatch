package com.technology.dronedispatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DroneDispatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneDispatchApplication.class, args);
	}

}
