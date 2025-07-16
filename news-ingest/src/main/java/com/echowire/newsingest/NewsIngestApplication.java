package com.echowire.newsingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsIngestApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsIngestApplication.class, args);
	}

}
