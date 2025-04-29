package com.my.springrabbitmqstompsample.model;

public record ChatMessage(String sender, String ip, String message) {
}
