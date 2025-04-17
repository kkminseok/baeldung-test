package com.my.springstompartemissample.model;

public record ChatMessage(
        String roomId,
        String sender,
        String content,
        MessageType type
) {
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        NOTICE
    }
}