package com.my.springwebfluxfilter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ExampleHandler {

    public Mono<ServerResponse> getName(ServerRequest request) {
        Mono<String> name = Mono.just(request.pathVariable("name"));
        return ServerResponse.ok().body(name, String.class);
    }
}
