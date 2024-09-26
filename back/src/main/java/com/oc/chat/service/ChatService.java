package com.oc.chat.service;

import com.oc.chat.model.ChatRoom;
import com.oc.chat.model.Message;
import com.oc.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<ChatRoom> getChatRoomById(String id) {
        return chatRepository.findById(id);
    }

    @Override
    public void saveMessage(Message message) {
        String chatRoomId =
                message.getSenderId()+"_"+message.getReceiverId();
        Optional<ChatRoom>
                chatRoom = chatRepository.findById(chatRoomId);
        if(chatRoom.isPresent()) {
            List<Message> messages = chatRoom.get().getMessages();
            messages.add(message);
        }
        else {
            ChatRoom chat = new ChatRoom();
            chat.setId(chatRoomId);
            chat.setClientId(Long.valueOf(message.getSenderId()));
            chat.setSupportId(Long.valueOf(message.getReceiverId()));
            chat.setStatus(true);
            List<Message> chatmessages = new ArrayList<>();
            chatmessages.add(message);
            chat.setMessages(chatmessages);
            chatRepository.save(chat);
        }

    }


}
