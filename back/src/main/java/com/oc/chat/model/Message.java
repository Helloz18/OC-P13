package com.oc.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Message {

    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private String date;

    public Message() {}
}
