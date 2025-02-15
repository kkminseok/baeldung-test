package com.my.springwebfluxfilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class ExampleRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ExampleHandler exampleHandler) {
        return RouterFunctions
                .route(GET("/players/{name}"), exampleHandler::getName)
                .filter(new ExampleHandlerFilterFunction());
    }
}
