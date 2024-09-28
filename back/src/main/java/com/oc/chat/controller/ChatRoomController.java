package com.oc.chat.controller;

import com.oc.chat.model.ChatRoom;
import com.oc.chat.model.Message;
import com.oc.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chatroom")
    public List<ChatRoom> displayAllChatRooms() {
        return chatService.getAllChatRooms();
    }

    /**
     * messages send by the frontend to /chat.sendMessages are then put in /chat/messages
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/chat/messages")
    public Message sendMessage(Message message) throws Exception {
        chatService.saveMessage(message);
        return message;
    }
}
