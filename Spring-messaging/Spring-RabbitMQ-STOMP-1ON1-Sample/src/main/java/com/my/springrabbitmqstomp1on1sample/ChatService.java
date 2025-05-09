package com.my.springrabbitmqstomp1on1sample;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendPrivateMessage(String username, String message) {
        messagingTemplate.convertAndSendToUser(
                username,          // 유저명 (Principal.getName())
                "/queue/messages", // 클라이언트는 /user/queue/messages로 구독
                message
        );
    }

    public List<String> getOnlineUsers(RedisTemplate<String, String> redisTemplate) {
        return new ArrayList<>(redisTemplate.opsForSet().members("online-users"));
    }
}
