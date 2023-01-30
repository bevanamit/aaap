package com.automation.aaap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AaapApplication {

	@Autowired
	AppConfig appConfig;
	public static void main(String[] args) {
		SpringApplication.run(AaapApplication.class, args);
	}

	@PostConstruct
	public void init() {
		appConfig.updateConfig();
	}

}
