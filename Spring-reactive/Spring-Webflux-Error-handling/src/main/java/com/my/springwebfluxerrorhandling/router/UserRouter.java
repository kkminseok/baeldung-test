package com.my.springwebfluxerrorhandling.router;

import com.my.springwebfluxerrorhandling.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;


@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(Handler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),handler::handleWithErrorResumeAndCustomException);
    }
}
