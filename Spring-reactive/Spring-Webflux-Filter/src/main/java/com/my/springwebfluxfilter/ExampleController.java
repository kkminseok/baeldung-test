package com.my.springwebfluxfilter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ExampleController {

    @GetMapping("/users/{name}")
    public Mono<String> getUser(@PathVariable String name) {
        return Mono.just(name);
    }

}
