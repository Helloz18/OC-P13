package com.oc.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "chatrooms")
public class ChatRoom {

    @Id
    private String id;
    private Long clientId;
    private Long supportId;
    private boolean status;
    private List<Message> messages;

    public ChatRoom() {
    }
}
