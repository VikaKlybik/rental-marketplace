package com.bsuir.service;

import com.bsuir.dto.MessageRequest;
import com.bsuir.dto.MessageResponse;

import java.util.List;

public interface ChatMessageService {
    MessageResponse save(MessageRequest messageDTO);

    List<MessageResponse> getChatMessages(Long senderId, Long recipientId);
}