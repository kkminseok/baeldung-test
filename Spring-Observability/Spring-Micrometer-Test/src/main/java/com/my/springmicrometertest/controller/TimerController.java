package com.my.springmicrometertest.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import org.springframework.boot.actuate.autoconfigure.tracing.TracingProperties.OpenTelemetry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class TimerController {

    private final Timer timer;
    private final MeterRegistry meterRegistry;
    private final OpenTelemetry openTelemetry;

    public TimerController(MeterRegistry meterRegistry, OpenTelemetry openTelemetry) {
        this.meterRegistry = meterRegistry;
        this.timer = Timer.builder("response.time")
                .description("Response time for requests")
                .register(meterRegistry);

        new ProcessorMetrics().bindTo(this.meterRegistry);
        new JvmMemoryMetrics().bindTo(this.meterRegistry);

        this.openTelemetry = openTelemetry;

    }

    @GetMapping("/timer")
    public String timedEndpoint() throws Exception {
        Timer.Sample sample = Timer.start(meterRegistry);

        try {
            Runnable task = () -> {
                System.out.println("Hello from named virtual thread! Thread: " + Thread.currentThread());
                try {
                    Thread.sleep(100); // 가상 스레드 내 작업 시뮬레이션
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            Thread virtualThread = Thread.ofVirtual()
                    .name("my-virtual-thread-1") // 스레드 이름 설정
                    .start(task);

            virtualThread.join(); // 가상 스레드가 종료될 때까지 대기

            return "success"; // 응답 반환
        } finally {
            // 타이머 종료 및 측정값 기록: try 블록이 끝나면 항상 실행됩니다.
            sample.stop(timer); // 올바른 Timer 인스턴스를 전달합니다.
            System.out.println("Endpoint timer stopped for my.endpoint.response.time");
        }
    }


}
