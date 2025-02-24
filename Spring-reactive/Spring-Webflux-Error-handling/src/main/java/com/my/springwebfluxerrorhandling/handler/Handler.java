package com.my.springwebfluxerrorhandling.handler;

import com.my.springwebfluxerrorhandling.exception.NameRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    public Mono<ServerResponse> handleWithErrorResumeAndCustomException(ServerRequest request) {
        return ServerResponse.ok()
                .body(sayHello(request)
                        .onErrorResume(e -> Mono.error(new NameRequiredException(
                                HttpStatus.BAD_REQUEST,
                                "username is required", e))), String.class);
    }

    public Mono<ServerResponse> handleWithErrorResumeAndFallbackMethod(ServerRequest request) {
        return sayHello(request)
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> sayHelloFallback()
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> handleWithErrorResumeAndDynamicFallback(ServerRequest request) {
        return sayHello(request)
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s))
                .onErrorResume(e -> Mono.just("Error " + e.getMessage())
                        .flatMap(s -> ServerResponse.ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue(s)));
    }

    public Mono<ServerResponse> handleWithErrorReturn(ServerRequest serverRequest) {
        return sayHello(serverRequest)
                .onErrorReturn("Hello Stranger")
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s));
    }

    public Mono<ServerResponse> handleWithGlobalErrorHandler(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(sayHello(serverRequest), String.class);
    }

    public Mono<String> sayHello(ServerRequest serverRequest) {
        try {
            return Mono.just("Hello " + serverRequest.queryParam("username").get());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private Mono<String> sayHelloFallback() {
        return Mono.just("Hello, Stranger");
    }
}
