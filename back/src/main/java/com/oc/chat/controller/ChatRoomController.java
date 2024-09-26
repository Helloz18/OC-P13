package com.oc.chat.controller;

import com.oc.chat.model.ChatRoom;
import com.oc.chat.model.Message;
import com.oc.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chatroom")
    public List<ChatRoom> displayAllChatRooms() {
        return chatService.getAllChatRooms();
    }

    @PostMapping("/chatroom")
    public ResponseEntity createChatRoom(@RequestBody ChatRoom chatRoom) {
        chatService.createChatRoom(chatRoom);
        return ResponseEntity.ok().body(chatRoom);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/chat/messages")
    public Message sendMessage(Message message, String chatRoomId) throws Exception {
        // Sauvegarde du message dans MongoDB
        chatService.saveMessage(message, chatRoomId);
        return message; // Diffuse le message à tous les abonnés de /chat/messages
    }
}
