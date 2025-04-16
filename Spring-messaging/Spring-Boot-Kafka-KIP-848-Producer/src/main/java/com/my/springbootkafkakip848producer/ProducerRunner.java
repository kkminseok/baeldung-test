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
        return new NewTopic("KIP848-topic", 5, (short) 1); // 파티션 2개, replication factor 1
    }

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 0; i < 20; i++) {
            String key = String.valueOf(i % 5); // 파티션 균등 분산
            String message = (i == 3 || i == 10 || i == 17) ? "slow-message-" + i : "message-" + i;
            template.send("KIP848-topic", key, message);
        }

    }
}