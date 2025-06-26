package com.my.springmicrometertest.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    private final Counter counter;

    private final MeterRegistry meterRegistry;

    public CounterController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.counter = Counter.builder("my_counter")
                .description("A simple counter for demonstration")
                .register(meterRegistry);
    }

    @GetMapping("/increment")
    public String incrementCounter() {
        counter.increment();
        return "Counter incremented: " + counter.count();
    }
}
