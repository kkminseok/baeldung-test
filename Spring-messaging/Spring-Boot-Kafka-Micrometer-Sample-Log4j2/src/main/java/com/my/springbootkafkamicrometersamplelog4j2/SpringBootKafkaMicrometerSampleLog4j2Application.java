package com.my.springbootkafkamicrometersamplelog4j2;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.MDC;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;

@Slf4j
@SpringBootApplication
public class SpringBootKafkaMicrometerSampleLog4j2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaMicrometerSampleLog4j2Application.class, args);
	}
	@Bean
	ProducerListener<Object, Object> kafkaProducerListener() {
		return new ProducerListener<>() {

			@Override
			public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
				log.info("Produced: " + producerRecord);
			}
		};
	}

	@Bean
	ApplicationRunner applicationRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> kafkaTemplate.sendDefault("test data");
	}

	@KafkaListener(topics = "${spring.kafka.template.default-topic}")
	void processData(ConsumerRecord<Object, Object> consumerRecord) {
		log.info("Received: " + consumerRecord);
	}
}
