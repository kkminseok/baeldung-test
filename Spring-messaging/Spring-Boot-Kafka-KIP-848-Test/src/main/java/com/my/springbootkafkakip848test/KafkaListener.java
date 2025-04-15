package com.my.springbootkafkakip848test;

import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "test-topic", groupId = "sample07-1")
    public void listenWithGroup1(String message) {
        System.out.println("Received message at group sample07-1: " + message);
    }

    @org.springframework.kafka.annotation.KafkaListener(topics = "test-topic", groupId = "sample07-2")
    public void listenWithGroup2(String message) {
        System.out.println("Received message at group sample07-2: " + message);
    }
}
