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
            String type = accessor.getFirstNativeHeader("type");
            if (username != null) {
                accessor.setUser(new StompPrincipal(username));  // 이 코드는 계속 써야 함 (for local handling)
                String sessionId = accessor.getSessionId();  // ✅ 세션ID도 여기서 받아둬야 함
                System.out.println("username: " + username + ", sessionId: " + sessionId);
                // 세션ID와 username 매핑을 Redis 등에 저장해야함
                redisTemplate.opsForValue().set("session:user:" + sessionId, username);
                redisTemplate.opsForValue().set("user:status:" + type + ":" + username, "online");
            }
        }
        else if (StompCommand.DISCONNECT.equals(command)) {
            String sessionId = accessor.getSessionId();
            String username = redisTemplate.opsForValue().get("session:user:" + sessionId);
            if (username != null) {
                String type = "yourType"; // 필요시 type도 저장해두고 꺼내면 됨
                System.out.println("연결해제: " + username);
                redisTemplate.opsForValue().set("user:status:" + type + ":" + username, "offline");
                redisTemplate.delete("session:user:" + sessionId); // 세션정보 삭제
            } else {
                System.out.println("연결해제: but username is null for sessionId=" + sessionId);
            }
        }
        return message;
    }
}
