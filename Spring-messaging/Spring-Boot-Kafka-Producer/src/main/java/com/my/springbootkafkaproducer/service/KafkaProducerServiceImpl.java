package com.my.springbootkafkaproducer.service;

import com.my.springbootkafkaproducer.model.Foo1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String topic, Object message) {
        log.info("topic:{},message:{}", topic, message);
        kafkaTemplate.send(topic, message);
    }

}
