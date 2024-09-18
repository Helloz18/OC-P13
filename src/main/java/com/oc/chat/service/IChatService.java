package com.oc.chat.service;

import com.oc.chat.model.ChatRoom;

import java.util.List;

public interface IChatService {

    void createChatRoom(ChatRoom chatRoom);

    List<ChatRoom> getAllChatRooms();
}
