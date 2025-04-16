package com.my.springbootkafkakip848consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@KafkaListener(groupId = "rebalance-consumer", topics = "KIP818-topic")
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaHandler
    public void consume(String message) {
        log.info("Consumed message: " + message +"\ntime " + LocalDateTime.now());
        if (message.contains("slow")) {
            try {
                Thread.sleep(5000); // 일부 Consumer는 일부러 느리게 동작
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        log.info("============finished consuming message: " + message + "\ntime " + LocalDateTime.now());

    }
}