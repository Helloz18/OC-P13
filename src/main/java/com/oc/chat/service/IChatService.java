package com.oc.chat.service;

import com.oc.chat.model.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    void createChatRoom(ChatRoom chatRoom);

    List<ChatRoom> getAllChatRooms();

    Optional<ChatRoom> getChatRoomById(String chatRoomId);
}
