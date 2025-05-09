package com.my.springrabbitmqstompsample.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    String sender;
    String ip;
    String message;
    String roomId;
    String type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    ZonedDateTime dateTime;
}
