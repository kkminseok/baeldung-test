package com.my.springbootkafkaproducer.service;

import com.my.springbootkafkaproducer.model.Foo1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, new Foo1(message));
    }

}
