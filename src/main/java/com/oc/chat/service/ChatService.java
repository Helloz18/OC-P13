package com.oc.chat.service;

import com.oc.chat.model.ChatRoom;
import com.oc.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService implements IChatService {

    @Autowired
    private ChatRepository chatRepository;


    @Override
    public void createChatRoom(ChatRoom chatRoom) {
        chatRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoom> chatrooms = new ArrayList<>();
        chatrooms = chatRepository.findAll();
        return chatrooms;
    }
}
