package com.my.springwebfluxmonofluxtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class SpringWebfluxMonoFluxTestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void givenMonoPublisher_whenSubscribeThenReturnSingleValue() {
		Mono<String> helloMono = Mono.just("hello");
		StepVerifier.create(helloMono)
				.expectNext("hello")
				.expectComplete()
				.verify();
	}

	@Test
	public void givenFluxPublisher_whenSubscribedThenReturnMultipleValues() {
		Flux<String> stringFlux = Flux.just("hello", "minseok");
		StepVerifier.create(stringFlux)
				.expectNext("hello")
				.expectNext("minseok")
				.expectComplete()
				.verify();
	}

	@Test
	public void givenFluxPublisher_whenSubscribeThenReturnMultipleValuesWithError() {
		Flux<String> stringFlux = Flux.just("hello", "minseok", "Error")
				.map(str -> {
					if (str.equals("Error"))
						throw new RuntimeException("Throwing Error");
					return str;
				});

		StepVerifier.create(stringFlux)
				.expectNext("hello")
				.expectNext("minseok")
				.expectError()
				.verify();
	}

}
