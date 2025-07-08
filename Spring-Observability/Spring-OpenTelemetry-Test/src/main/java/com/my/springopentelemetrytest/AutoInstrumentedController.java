package com.my.springopentelemetrytest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class AutoInstrumentedController {

    private Random random = new Random();

    @GetMapping("/auto-hello")
    public String autoHello(@RequestParam(defaultValue = "AutoWorld") String name) throws InterruptedException {
        log.info("Processing /auto-hello request for: {}", name);
        // 복잡한 비즈니스 로직 시뮬레이션
        simulateInternalWork();

        if (name.equalsIgnoreCase("error")) {
            throw new RuntimeException("Simulated error from auto-instrumented endpoint");
        }

        return "Hello, " + name + " (Auto-instrumented)!";
    }

    private void simulateInternalWork() throws InterruptedException {
        // 이 메서드 호출도 자동 계측될 수 있습니다 (필요한 경우 설정에 따라).
        Thread.sleep(random.nextInt(300) + 50); // 짧은 지연 시뮬레이션
        log.debug("Internal work simulated.");
    }
}