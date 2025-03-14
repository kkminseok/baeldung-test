package com.my.springwebfluxmonotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    void MonoJustTest() {
        Mono<String> mono = Mono.just("Hello");
        StepVerifier.create(mono)
                .expectNext("Hello")
                .verifyComplete();
    }

    @Test
    void MonoJustOrEmptyTest() {
        Mono<String> mono = Mono.justOrEmpty(null);

        StepVerifier.create(mono)
                .expectNext();
    }

    @Test
    void MonoDeferTest() {
        Mono<String> mono = Mono.defer(() -> {
            System.out.println("Generating Value");
            return Mono.just("Hello");
        });

        System.out.println("Before subscribing");

        StepVerifier.create(mono)
                .expectNext("Hello")
                .verifyComplete();
    }

    @Test
    void testMonoFromSupplier() {
        Mono<String> mono = Mono.fromSupplier(() -> {
            System.out.println("Generating Value");
            return "Hello";
        });

        System.out.println("Before subscribing");

        StepVerifier.create(mono)
                .expectNext("Hello")
                .verifyComplete();
    }
}
