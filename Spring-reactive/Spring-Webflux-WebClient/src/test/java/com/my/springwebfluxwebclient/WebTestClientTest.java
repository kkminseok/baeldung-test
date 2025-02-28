package com.my.springwebfluxwebclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import reactor.core.publisher.Mono;

@SpringBootTest
public class WebTestClientTest {


    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void binding_server_test(){
        WebTestClient testClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    public void binding_router() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.GET("/resource"),
                request -> ServerResponse.ok().build());

        WebTestClient.bindToRouterFunction(route)
                .build().get().uri("/resource")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }


    @Test
    public void Router방식_요청_테스트() {
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.GET("/my/test"),
                request -> ServerResponse.ok().build()
        );

        WebTestClient.bindToRouterFunction(route)
                .build().get().uri("/my/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Test
    public void Handler_테스트() {
        WebHandler handler = exchange -> Mono.empty();
        WebTestClient.bindToWebHandler(handler).build();
    }

    @Test
    public void ApplicationContext_테스트() {
        WebTestClient testClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void Controller_테스트() {
        //WebTestClient testClient = WebTestClient.bindToController(controllers).build();

    }
}
