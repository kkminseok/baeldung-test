package com.my.springwebfluxchaninningasnyc;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Component1 {
    public Mono<String> getData() {
        return Mono.just("Data from Component1");
    }
}

