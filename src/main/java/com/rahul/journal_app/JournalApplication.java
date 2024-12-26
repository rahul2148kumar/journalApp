package com.rahul.journal_app;

import com.rahul.journal_app.controller.JournalEntryControllerV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JournalApplication {

	private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(JournalApplication.class, args);
		logger.info("> Active Environment: {}", context.getEnvironment());
	}

}
