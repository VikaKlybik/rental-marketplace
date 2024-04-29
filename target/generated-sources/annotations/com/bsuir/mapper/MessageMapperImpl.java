package com.bsuir.mapper;

import com.bsuir.dto.MessageRequest;
import com.bsuir.dto.MessageResponse;
import com.bsuir.entity.Chat;
import com.bsuir.entity.Message;
import com.bsuir.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:25+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public Message toEntity(MessageRequest messageRequest) {
        if ( messageRequest == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.recipient( messageRequestToUser( messageRequest ) );
        message.sender( messageRequestToUser1( messageRequest ) );
        message.message( messageRequest.getMessage() );

        return message.build();
    }

    @Override
    public MessageResponse toDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageResponse messageResponse = new MessageResponse();

        messageResponse.setRecipientId( messageRecipientId( message ) );
        messageResponse.setSenderId( messageSenderId( message ) );
        messageResponse.setChatId( messageChatId( message ) );
        messageResponse.setId( message.getId() );
        messageResponse.setMessage( message.getMessage() );
        messageResponse.setTime( message.getTime() );

        return messageResponse;
    }

    @Override
    public List<MessageResponse> toListOfDto(List<Message> messageList) {
        if ( messageList == null ) {
            return null;
        }

        List<MessageResponse> list = new ArrayList<MessageResponse>( messageList.size() );
        for ( Message message : messageList ) {
            list.add( toDto( message ) );
        }

        return list;
    }

    @Override
    public Message partialUpdate(MessageRequest messageRequest, Message message) {
        if ( messageRequest == null ) {
            return message;
        }

        if ( messageRequest.getMessage() != null ) {
            message.setMessage( messageRequest.getMessage() );
        }

        return message;
    }

    protected User messageRequestToUser(MessageRequest messageRequest) {
        if ( messageRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( messageRequest.getRecipientId() );

        return user.build();
    }

    protected User messageRequestToUser1(MessageRequest messageRequest) {
        if ( messageRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( messageRequest.getSenderId() );

        return user.build();
    }

    private Long messageRecipientId(Message message) {
        if ( message == null ) {
            return null;
        }
        User recipient = message.getRecipient();
        if ( recipient == null ) {
            return null;
        }
        Long id = recipient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long messageSenderId(Message message) {
        if ( message == null ) {
            return null;
        }
        User sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        Long id = sender.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long messageChatId(Message message) {
        if ( message == null ) {
            return null;
        }
        Chat chat = message.getChat();
        if ( chat == null ) {
            return null;
        }
        Long id = chat.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
