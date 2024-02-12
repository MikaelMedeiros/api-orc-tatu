package com.codejokers.orctatu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class OrctatuApplication {

	public static void main(final String[] args) {
		SpringApplication.run(OrctatuApplication.class, args);
	}
}