package com.my.springbootkafkakip848producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerRunner implements ApplicationRunner {

    private final KafkaTemplate<String, String> template;

    ProducerRunner(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("KIP818-topic", 2, (short) 1); // 파티션 2개, replication factor 1
    }

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 20; i++) {
            template.send("KIP818-topic", String.valueOf(i), "message-" + i);
            if(i%3==0) {
                template.send("KIP818-topic",  String.valueOf(i), "slow-message");
            }
        }

    }
}