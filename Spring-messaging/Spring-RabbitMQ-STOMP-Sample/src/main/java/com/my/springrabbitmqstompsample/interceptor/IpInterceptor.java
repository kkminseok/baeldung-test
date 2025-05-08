package com.my.springrabbitmqstompsample.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class IpInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        System.out.println("interceptor: " + message.getHeaders());
        System.out.println("accessor: " + accessor.toString());
        System.out.println("command: " + command);
        if (command == null) {
            return message; // command가 없는 메시지는 무시
        }
        if (command.equals(StompCommand.SEND)) {
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            if (sessionAttributes != null) {
                HttpServletRequest httpRequest = (HttpServletRequest) sessionAttributes.get("HTTP_REQUEST");
                if (httpRequest != null) {
                    String ip = httpRequest.getHeader("X-Forwarded-For");
                    if (ip == null) {
                        ip = httpRequest.getRemoteAddr();
                    }
                    System.out.println("클라이언트 IP: " + ip);
                }
            }
        }
        return message;
    }
}
