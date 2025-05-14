package com.my.springrabbitmqstomp1on1sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트가 /app/chat.send 호출 시 동작
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage chatMessage) {
        log.info("Message from {} to {}", chatMessage.getSender(), chatMessage.getReceiver());

//        messagingTemplate.convertAndSend(
//                "/queue/"+chatMessage.getReceiver(),
//                chatMessage
//        );

        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(),
                "/queue/messages", chatMessage);
    }
}