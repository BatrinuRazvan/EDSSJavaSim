package com.edss.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

	@Bean
	public NotificationService notificationService() {
		NotificationService notificationService = new NotificationService();
		new DisasterDaemonInitializer(notificationService);
		return notificationService;
	}

}
