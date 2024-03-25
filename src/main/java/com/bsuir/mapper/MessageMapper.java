package com.bsuir.mapper;

import com.bsuir.dto.MessageRequest;
import com.bsuir.dto.MessageResponse;
import com.bsuir.entity.Message;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(source = "recipientId", target = "recipient.id")
    @Mapping(source = "senderId", target = "sender.id")
    Message toEntity(MessageRequest messageRequest);

    @Mapping(source = "recipient.id", target = "recipientId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "chat.id", target = "chatId")
    MessageResponse toDto(Message message);

    List<MessageResponse> toListOfDto(List<Message> messageList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Message partialUpdate(MessageRequest messageRequest, @MappingTarget Message message);
}