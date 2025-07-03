package com.my.springbootkafkaapacheavrohandle;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootKafkaApacheAvroHandleApplicationTests {

    @Container
    @ServiceConnection
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:4.0.0"));

    @Test
    void whenSendingMalformedMessage_thenSendToDLQ() throws Exception {
        stringKafkaTemplate()
                .send("minseok.article.published", "not a valid avro message!")
                .get();

        var dlqRecord = listenForOneMessage("minseok.article.published-dlt", ofSeconds(5L));

        assertThat(dlqRecord.value())
                .isEqualTo("not a valid avro message!");

        System.out.println("dlqRecord: " + dlqRecord.value());
    }

    private static ConsumerRecord<?, ?> listenForOneMessage(String topic, Duration timeout) {
        return KafkaTestUtils.getOneRecord(
                kafka.getBootstrapServers(), "test-group-id", topic, 0, false, true, timeout);
    }
    private static KafkaTemplate<Object, Object> stringKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        props.put("schema.registry.url", "mock://test");

        DefaultKafkaProducerFactory<Object, Object> producerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }

}
