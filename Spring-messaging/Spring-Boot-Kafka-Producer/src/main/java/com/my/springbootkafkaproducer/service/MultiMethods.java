package com.my.springbootkafkaproducer.service;

import com.my.springbootkafkaproducer.model.Bar2;
import com.my.springbootkafkaproducer.model.Foo2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@KafkaListener(id = "multiGroup", topics = {"foos", "bars"})
public class MultiMethods {

    private final ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor();

    @KafkaHandler
    public void foo(Foo2 foo) {
        log.info("Received Foo2: {}", foo);
        terminateMessage();
    }

    @KafkaHandler
    public void bar(Bar2 bar) {
        log.info("Received Bar2: {}", bar);
        terminateMessage();
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object unknown, @Headers Map<String, Object> headers) {
        log.info("Received unknown: {}", unknown);
        log.info(headers.toString());
        terminateMessage();
    }


    private void terminateMessage() {
        this.exec.execute(() -> log.info("Hit Enter to terminate..."));
    }
}
