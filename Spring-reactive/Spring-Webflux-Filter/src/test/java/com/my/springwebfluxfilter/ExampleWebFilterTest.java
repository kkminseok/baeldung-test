package com.my.springwebfluxfilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ExampleWebFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenUserNameIsMinseok_thenWebFilterIsApplied() {
        EntityExchangeResult<String> response = webTestClient.get()
                .uri("/users/minseok")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();
        Assertions.assertEquals("minseok", response.getResponseBody());
        Assertions.assertEquals("web-filter-test", response.getResponseHeaders().getFirst("web-filter"));
    }

    @Test
    public void whenUserNameIsTest_thenHandlerFilterFunctionIsNotApplied() {
        webTestClient.get().uri("/users/test")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenPlayerNameIsMinseok_thenWebFilterIsApplied() {
        EntityExchangeResult<String> result = webTestClient.get()
                .uri("/players/minseok")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();

        Assertions.assertEquals("minseok", result.getResponseBody());
        Assertions.assertEquals("web-filter-test",
                result.getResponseHeaders().getFirst("web-filter"));
    }

    @Test
    public void whenPlayerNameIsTest_thenHandlerFilterFunctionIsApplied() {
        webTestClient.get().uri("/players/test")
                .exchange()
                .expectStatus().isForbidden();
    }
}
