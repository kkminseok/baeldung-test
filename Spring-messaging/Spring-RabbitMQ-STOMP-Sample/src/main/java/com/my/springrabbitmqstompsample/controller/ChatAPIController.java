package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import com.my.springrabbitmqstompsample.model.ChatRoom;
import com.my.springrabbitmqstompsample.model.RoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatAPIController {

    private final RedisTemplate<String,String> stringRedisTemplate;
    private final RedisTemplate<String,ChatRoom> chatRoomRedisTemplate;

    @PostMapping("/api/chat/create-room/{username}")
    public String createRoom(@PathVariable String username) {
        String roomId = "T" + UUID.randomUUID().toString().substring(0, 5);
        RoomResponse roomResponse = RoomResponse.builder()
                .roomId(roomId)
                .build();
        log.info("=====================================createRoom: {}", roomResponse.getRoomId());
        String redisKey = "chat:room:set";  // Set은 자동 중복 제거
        stringRedisTemplate.opsForSet().add(redisKey, roomResponse.getRoomId());
        //todo: 상태는 Enum관리
        chatRoomRedisTemplate.opsForList().rightPush("chat:room:" + username, new ChatRoom(roomId, "WAIT"));
        return roomId;
    }

    @GetMapping("/api/chat/user-room/{username}")
    public List<ChatRoom> getUserRoom(@PathVariable String username) {
        return chatRoomRedisTemplate.opsForList().range("chat:room:" + username, 0, -1);
    }
}
