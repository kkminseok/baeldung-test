package com.my.springwebfluxwebclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;

@Component
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.GET("/resource"),
                request -> ServerResponse.ok().build()
        );
    }
}
