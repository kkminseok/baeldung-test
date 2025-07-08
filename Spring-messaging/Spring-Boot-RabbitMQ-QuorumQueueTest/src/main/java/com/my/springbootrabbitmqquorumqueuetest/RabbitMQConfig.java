package com.my.springbootrabbitmqquorumqueuetest;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String CLASSIC_QUEUE_NAME = "my.classic.queue";
    public static final String QUORUM_QUEUE_NAME = "my.quorum.queue";
    public static final String EXCHANGE_NAME = "my.direct.exchange";
    public static final String ROUTING_KEY_CLASSIC = "routing.classic";
    public static final String ROUTING_KEY_QUORUM = "routing.quorum";


    @Value("${spring.rabbitmq.addresses}")
    private String addresses;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // Direct Exchange 선언
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // Classic Queue 선언
    @Bean
    public Queue classicQueue() {
        // durable: true - RabbitMQ 서버 재시작 시 큐가 유지됩니다.
        // exclusive: false - 다른 연결에서 이 큐에 접근할 수 있습니다.
        // autoDelete: false - 컨슈머가 모두 끊어져도 큐가 삭제되지 않습니다.
        return new Queue(CLASSIC_QUEUE_NAME, true, false, false);
    }

    // Quorum Queue 선언
    @Bean
    public Queue quorumQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum"); // 쿼럼 큐 타입 지정
        // durable: true - 쿼럼 큐는 기본적으로 durable합니다.
        return new Queue(QUORUM_QUEUE_NAME, true, false, false, args);
    }

    // Classic Queue와 Exchange 바인딩
    @Bean
    public Binding classicBinding(Queue classicQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(classicQueue).to(directExchange).with(ROUTING_KEY_CLASSIC);
    }

    // Quorum Queue와 Exchange 바인딩
    @Bean
    public Binding quorumBinding(Queue quorumQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(quorumQueue).to(directExchange).with(ROUTING_KEY_QUORUM);
    }

    // 쿼럼 큐에 대한 정책 (선택 사항이지만 복구력 테스트에 유용)
    // 이 정책은 RabbitMQ 관리 UI 또는 rabbitmqctl 명령으로도 설정 가능합니다.
    // 여기서는 Spring Boot 애플리케이션 시작 시 자동으로 설정되도록 합니다.
    // 이 정책은 쿼럼 큐가 클러스터의 모든 노드에 복제되도록 합니다.
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        Map<String, Object> args = new HashMap<>();
        args.put("ha-mode", "all"); // 모든 노드에 복제
        args.put("ha-sync-mode", "automatic"); // 자동 동기화 모드
        rabbitAdmin.declareExchange(directExchange());
        rabbitAdmin.declareQueue(classicQueue());
        rabbitAdmin.declareBinding(classicBinding(classicQueue(), directExchange()));
        rabbitAdmin.declareQueue(quorumQueue());
        rabbitAdmin.declareBinding(quorumBinding(quorumQueue(), directExchange()));
        return rabbitAdmin;
    }
}