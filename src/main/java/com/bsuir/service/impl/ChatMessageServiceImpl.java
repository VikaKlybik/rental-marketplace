package com.bsuir.service.impl;

import com.bsuir.dto.MessageRequest;
import com.bsuir.dto.MessageResponse;
import com.bsuir.entity.Chat;
import com.bsuir.entity.Message;
import com.bsuir.mapper.MessageMapper;
import com.bsuir.repository.MessageRepository;
import com.bsuir.service.ChatMessageService;
import com.bsuir.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageResponse save(MessageRequest messageDTO) {
        Chat chat = chatRoomService.getChatById(
                messageDTO.getSenderId(),
                messageDTO.getRecipientId(),
                true
        );
        Message message = messageMapper.toEntity(messageDTO);
        message.setChat(chat);
        message.setIsRead(Boolean.FALSE);

        return messageMapper.toDto(
                messageRepository.save(message)
        );
    }

    @Override
    public List<MessageResponse> getChatMessages(Long senderId, Long recipientId) {
        Chat chat = chatRoomService.getChatById(
                senderId,
                recipientId,
                false
        );
        List<Message> messageList = messageRepository.findAllByChatId(chat.getId());
        return messageMapper.toListOfDto(messageList);
    }
}