package com.my.springwebfluxchaninningasnyc;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Component2 {
    public Mono<String> processData(String input) {
        return Mono.just(input + " -> Processed by Component2");
    }
}
