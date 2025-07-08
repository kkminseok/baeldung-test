package com.my.springbootrabbitmqquorumqueuetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    private final AtomicLong classicReceivedCount = new AtomicLong(0);
    private final AtomicLong quorumReceivedCount = new AtomicLong(0);

    @RabbitListener(queues = RabbitMQConfig.CLASSIC_QUEUE_NAME)
    public void receiveClassicMessage(String message) {
        classicReceivedCount.incrementAndGet();
        // log.debug("Received classic message: {}", message);
        if (classicReceivedCount.get() % 10000 == 0) {
            log.info("Classic Messages Received: {}", classicReceivedCount.get());
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUORUM_QUEUE_NAME)
    public void receiveQuorumMessage(String message) {
        quorumReceivedCount.incrementAndGet();
        // log.debug("Received quorum message: {}", message);
        if (quorumReceivedCount.get() % 10000 == 0) {
            log.info("Quorum Messages Received: {}", quorumReceivedCount.get());
        }
    }

    public long getClassicReceivedCount() {
        return classicReceivedCount.get();
    }

    public long getQuorumReceivedCount() {
        return quorumReceivedCount.get();
    }
}