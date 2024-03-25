package com.bsuir.service.impl;

import com.bsuir.dto.ChatResponse;
import com.bsuir.entity.Chat;
import com.bsuir.entity.User;
import com.bsuir.exception.ChatNotFoundException;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.repository.ChatRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public Chat getChatById(Long senderId, Long recipientId, boolean createNewRoomIfNotExists) {
        Optional<Chat> chatOptional = chatRepository.findByUserOneIdAndUserTwoId(senderId, recipientId);
        return chatOptional.orElseGet(() -> chatRepository.findByUserOneIdAndUserTwoId(recipientId, senderId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        Chat chat = createChat(senderId, recipientId);
                        return Optional.of(chat);
                    }
                    return Optional.empty();
                })
                .orElseThrow(ChatNotFoundException::new));
    }

    private Chat createChat(Long senderId, Long recipientId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new UserNotFoundException(recipientId));
        Chat chat = Chat.builder()
                .userOne(sender)
                .userTwo(recipient)
                .build();
        return chatRepository.save(chat);
    }

    @Override
    public List<ChatResponse> getAllChatForUser(String username) {
        List<Chat> chatList = chatRepository.findByUsername(username);
        return chatList.stream()
                .map(convertChatToChatResponse(username))
                .toList();
    }

    private Function<Chat, ChatResponse> convertChatToChatResponse(String username) {
        return chat -> {
            String title = chat.getUserTwo().getUserDetails().getLastName()
                    + " "
                    + chat.getUserTwo().getUserDetails().getFirstName();
            String userIcon = chat.getUserTwo().getUserDetails().getIconUrl();
            Long userId = chat.getUserTwo().getId();
            if (chat.getUserTwo().getUsername().equals(username)) {
                title = chat.getUserOne().getUserDetails().getLastName()
                        + " "
                        + chat.getUserOne().getUserDetails().getFirstName();
                userIcon = chat.getUserOne().getUserDetails().getIconUrl();
                userId = chat.getUserOne().getId();
            }

            return ChatResponse.builder()
                    .id(chat.getId())
                    .title(title)
                    .userIcon(userIcon)
                    .withUserId(userId)
                    .build();
        };
    }
}