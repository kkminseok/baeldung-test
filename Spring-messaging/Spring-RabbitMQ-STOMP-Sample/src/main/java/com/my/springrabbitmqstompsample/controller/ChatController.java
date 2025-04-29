package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage sendChatMessage(StompHeaderAccessor ha, ChatMessage chatMessage) {
        log.info(chatMessage.toString());
        String ip = (String) ha.getSessionAttributes().get("ip").toString();
        log.info(ip.toString());
        return new ChatMessage(chatMessage.sender(),  ip, chatMessage.message());
    }
}
