package com.my.springrabbitmqstompsample.controller;

import com.my.springrabbitmqstompsample.model.ChatMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class ChatHistoryControllerTest {


    private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
    private static final int REDIS_PORT = 6379;
    private static final GenericContainer REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer(REDIS_IMAGE)
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(REDIS_PORT)
                .toString());
    }

    @Autowired
    private RedisTemplate<String, ChatMessage> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnChatMessagesFromRedis() throws Exception {
        String roomKey = "chat:room:1";
        ChatMessage chatMessage = new ChatMessage("system", "127.0.0.1", "message","1");
        redisTemplate.opsForList().leftPush(roomKey, chatMessage);

        mockMvc.perform(get("/api/chat/rooms/1/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("message"))
                .andExpect(jsonPath("$[0].sender").value("system"))
                .andExpect(jsonPath("$[0].ip").value("127.0.0.1"))
                .andExpect(jsonPath("$[0].roomId").value("1"));
    }

}