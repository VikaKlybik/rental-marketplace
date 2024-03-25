package com.bsuir.service;

import com.bsuir.dto.ChatResponse;
import com.bsuir.entity.Chat;

import java.util.List;

public interface ChatRoomService {
    Chat getChatById(Long senderId, Long recipientId, boolean createNewRoomIfNotExists);
    List<ChatResponse> getAllChatForUser(String username);
}