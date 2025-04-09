package com.my.springbootkafkaembeddedsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class SpringBootKafkaEmbeddedSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaEmbeddedSampleApplication.class, args);
	}


	@KafkaListener(id ="sampleListener", topics = "topic1")
	void listenForTopic(String payload) {
		System.out.println("Received: " + payload);
	}


}
