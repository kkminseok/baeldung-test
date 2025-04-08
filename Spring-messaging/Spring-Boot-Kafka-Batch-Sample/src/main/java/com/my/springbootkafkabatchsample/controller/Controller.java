package com.my.springbootkafkabatchsample.controller;


import com.my.springbootkafkabatchsample.model.Foo1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final KafkaTemplate<Object, Object> template;

    @PostMapping("/send/foos/{what}")
    public void sendFoo(@PathVariable String what) {
        log.info("sample3");
        template.executeInTransaction(template -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                    .map(s -> new Foo1(s))
                    .forEach(foo -> {
                        log.info("data: {}", foo);
                        template.send("topic2", foo);
                    });
            return null;
        });
    }

}
