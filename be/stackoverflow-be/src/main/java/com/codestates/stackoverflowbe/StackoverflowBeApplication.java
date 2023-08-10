package com.codestates.stackoverflowbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StackoverflowBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackoverflowBeApplication.class, args);
	}

}
