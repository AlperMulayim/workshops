package com.alper.tutorscheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TutorSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorSchedulingApplication.class, args);
	}

}
