package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{roomId}")
    //@SendTo("/topic/chat")
    public void sendChatMessage(@DestinationVariable String roomId, StompHeaderAccessor ha, ChatMessage chatMessage) {
        log.info(chatMessage.toString());
        String ip = (String) ha.getSessionAttributes().get("ip").toString();
        log.info(ip.toString());
        String redisKey = "chat:room:" + chatMessage.roomId();
        log.info(redisKey);
        redisTemplate.opsForList().rightPush(redisKey, chatMessage);
        redisTemplate.opsForList().trim(redisKey, -100, -1); // 최근 100개만 유지
        template.convertAndSend("/topic/chat." + chatMessage.roomId() , new ChatMessage(chatMessage.sender(),  ip, chatMessage.message(), chatMessage.roomId()));
        //return new ChatMessage(chatMessage.sender(),  ip, chatMessage.message(), chatMessage.roomId());
    }
}
