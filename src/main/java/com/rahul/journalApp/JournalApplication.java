package com.rahul.journalApp;

import com.rahul.journalApp.controller.JournalEntryControllerV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JournalApplication {

	private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(JournalApplication.class, args);
		logger.info("> Active Environment: {}", context.getEnvironment().getActiveProfiles()[0]);
	}

}
