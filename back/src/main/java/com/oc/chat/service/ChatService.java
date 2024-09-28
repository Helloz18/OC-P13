package com.oc.chat.service;

import com.oc.chat.model.ChatRoom;
import com.oc.chat.model.Message;
import com.oc.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return chatRepository.findAll();
    }

    @Override
    public Optional<ChatRoom> getChatRoomById(String id) {
        return chatRepository.findById(id);
    }

    @Override
    public void saveMessage(Message message) {
        String chatRoomId = message.getSenderId()+"_"+message.getReceiverId();
        String chatRoomIdMiror = message.getReceiverId()+"_"+message.getSenderId();

        //on veut comparer le chatRoomIdMiror, dans le cas où on a la réponse du support
        // si le chatRoomIdMiror existe, alors c'est lui qu'on considère comme Id de chat

        try {
            Optional<ChatRoom>
                    chatRoom =
                    chatRepository.findById(chatRoomId);
            Optional<ChatRoom> chatRoomMiror = chatRepository.findById(chatRoomIdMiror);
            if(chatRoom.isEmpty() && chatRoomMiror.isEmpty()) {
                ChatRoom
                        chat =
                        new ChatRoom();
                chat.setId(chatRoomId);
                chat.setClientId(Long.valueOf(message.getSenderId()));
                chat.setSupportId(Long.valueOf(message.getReceiverId()));
                chat.setStatus(true);
                List<Message>
                        chatmessages =
                        new ArrayList<>();
                chatmessages.add(message);
                chat.setMessages(chatmessages);
                chatRepository.save(chat);
            }
            else if(!chatRoomMiror.isEmpty()) {
                List<Message>
                        messages =
                        chatRoomMiror.get().getMessages();
                messages.add(message);
                chatRepository.save(chatRoomMiror.get());
            }
            else {
                List<Message>
                        messages =
                        chatRoom.get().getMessages();
                messages.add(message);
                chatRepository.save(chatRoom.get());
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
