package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import com.my.springrabbitmqstompsample.model.RoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate template;

    @MessageMapping("/chat/{roomId}")
    //@SendTo("/topic/chat")
    public void sendChatMessage(@DestinationVariable String roomId, StompHeaderAccessor ha, ChatMessage chatMessage) {
        log.info(chatMessage.toString());
        String ip = (String) ha.getSessionAttributes().get("ip").toString();
        chatMessage.setIp(ip);
        chatMessage.setDateTime(ZonedDateTime.now());
        String redisKey = "chat:room:" + chatMessage.getRoomId();
        redisTemplate.opsForList().rightPush(redisKey, chatMessage);
        redisTemplate.opsForList().trim(redisKey, -100, -1); // 최근 100개만 유지

        log.info("chat: {}", chatMessage.toString());
        //log.info("agentId: {}", getAgentId());

        template.convertAndSend("/topic/room." + chatMessage.getRoomId(), new ChatMessage(chatMessage.getSender(), ip, chatMessage.getMessage(), chatMessage.getRoomId(), chatMessage.getType(), ZonedDateTime.now()));
        log.info("메시지 보냈다. :{}", "/topic/room." + chatMessage.getRoomId());
    }

    private String getAgentId() {
        return (String) rabbitTemplate.convertSendAndReceive("chat.connect", "hello");
    }



}
