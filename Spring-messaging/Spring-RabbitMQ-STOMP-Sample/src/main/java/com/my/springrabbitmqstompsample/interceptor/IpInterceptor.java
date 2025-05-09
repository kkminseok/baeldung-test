package com.my.springrabbitmqstompsample.interceptor;

import com.my.springrabbitmqstompsample.model.StompPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class IpInterceptor implements ChannelInterceptor {

    final private RedisTemplate<String, String> redisTemplate;

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
        if (StompCommand.SEND.equals(command)) {
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
        } else if (StompCommand.CONNECT.equals(command)) {
            String username = accessor.getFirstNativeHeader("username");
            if (username != null) {
                accessor.setUser(new StompPrincipal(username));  // 여기서 Principal 주입
                System.out.println("username: " + username);
                redisTemplate.opsForValue().set("user:status:" + username, "online");
            }

        } else if (StompCommand.DISCONNECT.equals(command)) {
            Principal user = accessor.getUser();
            if (user != null) {
                // ✅ 연결 해제시 Redis 상태 "offline"으로 변경
                redisTemplate.opsForValue().set("user:status:" + user.getName(), "offline");
            }
        }
        return message;
    }
}
