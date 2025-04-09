package com.my.springbootkafkaembeddedsample;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.errors.TimeoutException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@DirtiesContext
class Sample05Application2Tests {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void testKafkaTemplateSend() {
        assertThatExceptionOfType(KafkaException.class)
                .isThrownBy(() ->
                        this.kafkaTemplate.send("nonExistingTopic", "fake data").get(10, TimeUnit.SECONDS))
                .withRootCauseExactlyInstanceOf(TimeoutException.class)
                .withMessageContaining("Send failed")
                .withStackTraceContaining("Topic nonExistingTopic not present in metadata");
    }

}