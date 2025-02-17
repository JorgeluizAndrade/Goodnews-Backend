package com.project.goodnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = {"com.project.goodnews.mapper", "com.project.goodnews", "com.project.goodnews.security"})
public class GoodnewsApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoodnewsApplication.class, args);
	} 	
}
