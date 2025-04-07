package com.my.springbootkafkaproducer.controller;

import com.my.springbootkafkaproducer.model.Bar1;
import com.my.springbootkafkaproducer.model.Foo1;
import com.my.springbootkafkaproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;

    // sample 1
//    @PostMapping(path = "/send/foo/{what}")
//    public void send(@PathVariable String what) {
//        kafkaProducerService.sendMessage("topic1", what);
//    }

    @PostMapping(path = "/send/foo/{what}")
    public void sendFoo(@PathVariable String what) {
        kafkaProducerService.sendMessage("foos", new Foo1(what));
    }

    @PostMapping(path = "/send/bar/{what}")
    public void sendBar(@PathVariable String what) {
        kafkaProducerService.sendMessage("bars", new Bar1(what));
    }

    @PostMapping(path = "/send/unknown/{what}")
    public void sendUnknown(@PathVariable String what) {
        kafkaProducerService.sendMessage("bars", what);
    }
}
