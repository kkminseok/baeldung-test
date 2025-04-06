package com.my.springwebfluxmonofluxtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    void 바구니_속_과일종류_중복없이_각_종류별_개수_나누기() {
        basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();
            final Mono<Map<String,Long>> countFruitsMono = Flux.fromIterable(basket)
                    .groupBy(fruit -> fruit)
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String,Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            }))
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() {{
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }});
        })
    }
}
