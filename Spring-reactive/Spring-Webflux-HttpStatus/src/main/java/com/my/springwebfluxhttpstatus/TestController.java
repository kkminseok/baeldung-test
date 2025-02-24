package com.my.springwebfluxhttpstatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @GetMapping(value = "/ok", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> ok() {
        return Flux.just("ok");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping(value = "/no-content", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> noContent() {
        return Flux.empty();
    }

    @GetMapping(value = "/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> accepted(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.ACCEPTED);
        return Flux.just("accepted");
    }

    @GetMapping(value = "/bad-request")
    public Mono<String> badRequest(){
        return Mono.error(new IllegalArgumentException());
    }

    @GetMapping(value = "/unauthorized")
    public ResponseEntity<Mono<String>> unauthorized(){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .header("X-Reason", "user-invalid")
                .body(Mono.just("unauthorized"));
    }
}
