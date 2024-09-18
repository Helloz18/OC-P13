package com.oc.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Message {

    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private String date;
}
