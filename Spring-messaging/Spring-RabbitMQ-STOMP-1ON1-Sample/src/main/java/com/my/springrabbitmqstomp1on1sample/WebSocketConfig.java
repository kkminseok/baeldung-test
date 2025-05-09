package com.my.springrabbitmqstomp1on1sample;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompConnectInterceptor stompConnectInterceptor;

    /**
     * Configure the message broker for handling messages from clients.
     * - Enables a STOMP broker relay to RabbitMQ (acting as external broker).
     * - Sets destination prefixes for application messages.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable an external STOMP broker (like RabbitMQ) and define destination prefixes it will handle
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")      // Hostname of the RabbitMQ server
                .setRelayPort(61613)             // STOMP port on RabbitMQ
                .setClientLogin("guest")         // Login username for clients
                .setClientPasscode("guest")      // Login password for clients
                .setSystemLogin("guest")         // Login username for system (Spring) connections
                .setSystemPasscode("guest");
//                .setUserDestinationBroadcast("/topic/unresolved-user")
//                .setUserRegistryBroadcast("/topic/registry");     // Login password for system (Spring) connections

        // Application-level destination prefix for messages handled by @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompConnectInterceptor);
    }

}
