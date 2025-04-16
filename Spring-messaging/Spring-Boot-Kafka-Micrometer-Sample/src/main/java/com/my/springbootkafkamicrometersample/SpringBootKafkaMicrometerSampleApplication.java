package com.my.springbootkafkamicrometersample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;

@SpringBootApplication
public class SpringBootKafkaMicrometerSampleApplication {

	private static final Log LOG = LogFactory.getLog(SpringBootKafkaMicrometerSampleApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaMicrometerSampleApplication.class, args);
	}

	@Bean
	ProducerListener<Object, Object> kafkaProducerListener() {
		return new ProducerListener<>() {

			@Override
			public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
				LOG.info("Produced: " + producerRecord);
			}

		};
	}

	@Bean
	ApplicationRunner applicationRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> kafkaTemplate.sendDefault("test data");
	}

	@KafkaListener(topics = "${spring.kafka.template.default-topic}")
	void processData(ConsumerRecord<Object, Object> consumerRecord) {
		LOG.info("Received: " + consumerRecord);
	}

}
