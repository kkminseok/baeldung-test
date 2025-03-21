package com.my.springwebfluxchaninningasnyc;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Component3 {
    public Mono<String> finalizeData(String input) {
        return Mono.just(input + " -> Finalized by Component3");
    }
}