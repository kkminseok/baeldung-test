package com.my.springbootkafkaapacheavrohandle.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.Map;

@Configuration
public class DlqConfig {

    @Bean
    DefaultErrorHandler errorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
        return new DefaultErrorHandler(deadLetterPublishingRecoverer);
    }

    @Bean
    DeadLetterPublishingRecoverer dlqPublishingRecoverer(KafkaTemplate<byte[], byte[]> bytesKafkaTemplate) {
        return new DeadLetterPublishingRecoverer(bytesKafkaTemplate);
    }

    @Bean("bytesKafkaTemplate")
    KafkaTemplate<?, ?> bytesTemplate(ProducerFactory<?, ?> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory,
                Map.of(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName()));
    }
}
