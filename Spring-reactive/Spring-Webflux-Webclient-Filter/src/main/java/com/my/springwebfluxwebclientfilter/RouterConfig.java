package com.my.springwebfluxwebclientfilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/hello"), this::helloHandler)
                .andRoute(POST("/echo"), this::echoHandler);
    }

    private Mono<ServerResponse> helloHandler(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello, WebFlux!");
    }

    private Mono<ServerResponse> echoHandler(ServerRequest request) {
        return request.bodyToMono(String.class)
                .flatMap(body -> ServerResponse.ok().bodyValue("Echo: " + body));
    }
}
