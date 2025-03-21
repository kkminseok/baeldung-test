package com.my.springwebfluxchaninningasnyc;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
class ChainingServiceTest {
    @Autowired
    private ChainingService chainingService;

    @Test
    public void testExecuteChain() {
        // 실제 흐름 테스트 실행
        Mono<String> result = chainingService.executeChain();

        result.subscribe(System.out::println);
        // StepVerifier를 이용한 비동기 검증
        StepVerifier.create(result)
                .expectNext("Data from Component1 -> Processed by Component2 -> Finalized by Component3")
                .verifyComplete();
    }
}