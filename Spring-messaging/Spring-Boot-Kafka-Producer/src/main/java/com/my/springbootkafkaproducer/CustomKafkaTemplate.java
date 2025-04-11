package com.my.springbootkafkaproducer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CustomKafkaTemplate<K, V> extends KafkaTemplate<K, V> {

    public CustomKafkaTemplate(ProducerFactory<K, V> producerFactory) {
        super(producerFactory);
    }

    public CustomKafkaTemplate(ProducerFactory<K, V> producerFactory, Map<String, Object> configOverrides) {
        super(producerFactory, configOverrides);
    }

    public CustomKafkaTemplate(ProducerFactory<K, V> producerFactory, boolean autoFlush) {
        super(producerFactory, autoFlush);
    }

    public CustomKafkaTemplate(ProducerFactory<K, V> producerFactory, boolean autoFlush, Map<String, Object> configOverrides) {
        super(producerFactory, autoFlush, configOverrides);
    }

    @Override
    public CompletableFuture<SendResult<K, V>> send(String topic, V data) {
        ProducerRecord<K, V> record = new ProducerRecord<>(topic, data);

        // __TypeId__ 헤더 자동 추가
        if (data != null) {
            record.headers().add("__TypeId__", data.getClass().getName().getBytes(StandardCharsets.UTF_8));
        }

        return super.send(record);
    }

}
