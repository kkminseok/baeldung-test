package com.my.springwebfluxwebclientfilter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest
class SpringWebfluxWebclientFilterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void filer_basic_test() {
		ExchangeFilterFunction filterFunction = (clientRequest, nextFilter) -> {
			System.out.println("WebClient fitler executed");
			return nextFilter.exchange(clientRequest);
		};

		WebClient webClient = WebClient.builder().filter(filterFunction).build();

		webClient.get().uri("https://www.google.com").exchange().block();
	}

	@Test
	void count_filter_test() {
		AtomicInteger count = new AtomicInteger(0);
		ExchangeFilterFunction filterFunction = (clientRequest, nextFilter) -> {
			HttpMethod method = clientRequest.method();
			if(method == HttpMethod.GET) {
				count.incrementAndGet();
			}

			return nextFilter.exchange(clientRequest);
		};

		WebClient webClient = WebClient.builder().filter(filterFunction).build();
		for(int i =0; i< 50; ++i) {
			webClient.get().uri("https://www.google.com").exchange().block();
		}
		System.out.println("count: " + count.get());
	}

	@Test
	void log_filter_test() {
		ExchangeFilterFunction loggingFilter = (clientRequest, nextFilter) -> {
			System.out.println("Sending request " + clientRequest.method() + " " + clientRequest.url());
			return nextFilter.exchange(clientRequest);
		};

		WebClient webClient = WebClient.builder().filter(loggingFilter).build();

		webClient.get().uri("https://www.google.com").exchange().block();
	}

	@Test
	void authentication_filter_test() {
		WebClient webClient = WebClient.builder().filter(ExchangeFilterFunctions.basicAuthentication("minseok", "123"))
				.build();
	}

}
