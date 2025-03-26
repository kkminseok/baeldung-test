package com.my.springwebfluxmonofluxtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class MonoFluxTest {
    final List<String> basket1 = Arrays.asList(new String[]{"kiwi", "orange", "lemon", "orange", "lemon", "kiwi"});
    final List<String> basket2 = Arrays.asList(new String[]{"banana", "lemon", "lemon", "kiwi"});
    final List<String> basket3 = Arrays.asList(new String[]{"strawberry", "orange", "lemon", "grape", "strawberry"});
    List<List<String>> baskets;
    Flux<List<String>> basketFlux;

    @BeforeEach
    void setUp() {
        baskets = Arrays.asList(basket1, basket2, basket3);
         basketFlux = Flux.fromIterable(baskets);
    }

    @Test

}
