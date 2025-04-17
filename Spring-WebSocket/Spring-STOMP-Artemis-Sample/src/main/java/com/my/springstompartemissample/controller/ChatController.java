package com.my.springstompartemissample.controller;

import com.my.springstompartemissample.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        log.info("Message received: " + message.toString());
        messagingTemplate.convertAndSend("/topic/" + message.roomId(), message);
        return message;
    }

    @MessageMapping("/chat.private.{receiver}")
    public void sendPrivateMessage(@DestinationVariable String receiver, @Payload ChatMessage message, SimpMessagingTemplate template) {
        template.convertAndSend(receiver, message);
    }
}
