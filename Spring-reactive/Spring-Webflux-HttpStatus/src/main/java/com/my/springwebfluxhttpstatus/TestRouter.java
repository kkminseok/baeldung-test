package com.my.springwebfluxhttpstatus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class TestRouter {

    @Bean
    public RouterFunction<ServerResponse> notFound() {
        return RouterFunctions
                .route(GET("/statues/not-found"),
                        request -> ServerResponse.notFound().build());
    }
}
