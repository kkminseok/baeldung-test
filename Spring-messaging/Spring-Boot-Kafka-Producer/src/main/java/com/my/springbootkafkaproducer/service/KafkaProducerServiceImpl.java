package com.my.springbootkafkaproducer.service;

import com.my.springbootkafkaproducer.model.Foo1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(String topic, Object message) {
        log.info("topic:{},message:{}", topic, message);
        ProducerRecord<Object, Object> record = new ProducerRecord<>(topic,message);
        record.headers().add("__TypeId__", message.getClass().getName().getBytes(StandardCharsets.UTF_8));
        kafkaTemplate.send(record);
    }

}
