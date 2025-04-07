package com.my.springbootkafkaproducer.controller;

import com.my.springbootkafkaproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping(path = "/send/foo/{what}")
    public void send(@PathVariable String what) {
        kafkaProducerService.sendMessage("topic1", what);
    }
}
