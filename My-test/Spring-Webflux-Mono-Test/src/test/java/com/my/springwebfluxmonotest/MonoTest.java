package com.my.springwebfluxmonotest;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

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
    void MonoDefer_UUID_test() {
        Mono<String> defer = Mono.defer(() -> {
            System.out.println("Mono defer");
            return Mono.just(UUID.randomUUID().toString());
        });

        defer.subscribe(System.out::println);
        defer.subscribe(System.out::println);
    }

    @Test
    void MonoFromCallable_UUID_test() {
        Mono<String> callable = Mono.fromCallable(() -> {
            System.out.println("Callable 실행");
            return UUID.randomUUID().toString();
        });

        callable.subscribe(System.out::println);
        callable.subscribe(System.out::println);
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
