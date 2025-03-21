package com.my.springwebfluxchaninningasnyc;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChainingService {

    private final Component1 component1;
    private final Component2 component2;
    private final Component3 component3;

    public ChainingService(Component1 component1, Component2 component2, Component3 component3) {
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
    }

    public Mono<String> executeChain() {
        return component1.getData()
                .flatMap(data -> component2.processData(data))
                .flatMap(processedData -> component3.finalizeData(processedData));
    }
}