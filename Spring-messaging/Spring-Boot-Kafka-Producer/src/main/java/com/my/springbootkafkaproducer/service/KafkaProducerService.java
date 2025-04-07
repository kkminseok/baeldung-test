package com.my.springbootkafkaproducer.service;

public interface KafkaProducerService {

    void sendMessage(String topic, String message);
}
