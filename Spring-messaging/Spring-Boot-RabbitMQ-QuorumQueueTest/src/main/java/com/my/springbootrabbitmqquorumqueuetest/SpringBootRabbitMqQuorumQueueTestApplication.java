package com.my.springbootrabbitmqquorumqueuetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootRabbitMqQuorumQueueTestApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootRabbitMqQuorumQueueTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitMqQuorumQueueTestApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(MessageProducer producer, MessageConsumer consumer) {
		return args -> {
			log.info("Starting RabbitMQ Resilience Test Application...");

			// 메시지 생산 시작
			producer.startProducing();

			// 메시지 소비를 위한 충분한 시간 대기
			log.info("Waiting for consumers to process messages...");
			Thread.sleep(30000); // 30초 대기 (필요에 따라 조절)

			log.info("--- Test Results ---");
			log.info("Total Classic Messages Sent: {}", producer.classicSentCount.get());
			log.info("Total Classic Messages Received: {}", consumer.getClassicReceivedCount());
			log.info("Total Quorum Messages Sent: {}", producer.quorumSentCount.get());
			log.info("Total Quorum Messages Received: {}", consumer.getQuorumReceivedCount());

			log.info("RabbitMQ Resilience Test Application finished.");
			System.exit(0); // 애플리케이션 종료
		};
	}
}
