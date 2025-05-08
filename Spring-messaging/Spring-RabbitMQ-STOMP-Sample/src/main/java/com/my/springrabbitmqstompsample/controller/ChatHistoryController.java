package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatHistoryController {

    private final RedisTemplate<String, ChatMessage> redisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;

    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> getChatHistory(@PathVariable String roomId) {
        String key = "chat:room:" + roomId;
        List<ChatMessage> messages = redisTemplate.opsForList().range(key, 0, -1);// 전체 조회
        //log.info(messages.toString());
        return messages;
    }

    @GetMapping("/room/list")
    public List<String> getRoomList() {
        return stringRedisTemplate.opsForSet().members("chat:room:set").stream().toList();
    }
}
