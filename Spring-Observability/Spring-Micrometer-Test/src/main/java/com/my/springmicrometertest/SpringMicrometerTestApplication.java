package com.my.springmicrometertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMicrometerTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringMicrometerTestApplication.class, args);
	}

}
