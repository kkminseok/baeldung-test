package com.my.springbootrabbitmqquorumqueuetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final int totalProducers = 100_000; // 총 프로듀서 수
    private final int producersPerBatch = 1000; // 한 번에 시작할 프로듀서 수 (배치 크기)
    private final long batchDelayMillis = 2000; // 각 배치 시작 전 대기 시간 (밀리초)

    private final int messagesPerProducer = 1; // 각 프로듀서가 보낼 메시지 수

    public final AtomicLong classicSentCount = new AtomicLong(0); // public으로 변경하여 외부에서 접근 가능하도록
    public final AtomicLong quorumSentCount = new AtomicLong(0);  // public으로 변경하여 외부에서 접근 가능하도록

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void startProducing() {
        log.info("Starting message production with a total of {} virtual threads, in batches of {}...", totalProducers, producersPerBatch);

        // 가상 스레드 풀 생성 (Java 21+)
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < totalProducers; i += producersPerBatch) {
                final int startProducerId = i;
                final int endProducerId = Math.min(i + producersPerBatch, totalProducers);

                log.info("Starting batch of producers from {} to {}. Total sent classic: {}, quorum: {}",
                        startProducerId, endProducerId -1, classicSentCount.get(), quorumSentCount.get());

                for (int pId = startProducerId; pId < endProducerId; pId++) {
                    final int producerId = pId;
                    executor.submit(() -> {
                        try {
                            for (int j = 0; j < messagesPerProducer; j++) {
                                String quorumMessage = "Quorum Message from Producer " + producerId + " - " + j;
                                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_QUORUM, quorumMessage);
                                quorumSentCount.incrementAndGet();

                                String classicMessage = "Classic Message from Producer " + producerId + " - " + j;
                                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_CLASSIC, classicMessage);
                                classicSentCount.incrementAndGet();
                            }
                        } catch (Exception e) {
                            log.error("Error sending message from producer {}: {}", producerId, e.getMessage());
                        }
                    });
                }

                // 각 배치 시작 전 잠시 대기하여 부하를 점진적으로 증가시킴
                if (i + producersPerBatch < totalProducers) {
                    try {
                        Thread.sleep(batchDelayMillis);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.warn("Batch delay interrupted.");
                    }
                }
            }

            executor.shutdown();
            log.info("All producer batches submitted. Waiting for completion...");
            executor.awaitTermination(10, TimeUnit.MINUTES); // 충분한 시간 대기
            log.info("Message production finished.");
            log.info("Final Total Classic Messages Sent: {}", classicSentCount.get());
            log.info("Final Total Quorum Messages Sent: {}", quorumSentCount.get());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Message production interrupted: {}", e.getMessage());
        }
    }
}