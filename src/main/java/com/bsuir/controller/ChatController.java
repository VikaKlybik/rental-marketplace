package com.bsuir.controller;

import com.bsuir.dto.ChatRequest;
import com.bsuir.dto.ChatResponse;
import com.bsuir.dto.MessageRequest;
import com.bsuir.dto.MessageResponse;
import com.bsuir.entity.ChatNotification;
import com.bsuir.entity.User;
import com.bsuir.service.ChatMessageService;
import com.bsuir.service.ChatRoomService;
import com.bsuir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @GetMapping("/chat")
    public String getUserChats(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.getUserByUsername(user.getUsername()));
        return "chat";
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<MessageResponse>> getChatMessages(@PathVariable Long recipientId, @PathVariable Long senderId) {
        return ResponseEntity.ok(
                chatMessageService.getChatMessages(senderId, recipientId)
        );
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageRequest messageRequest) {
        MessageResponse message = chatMessageService.save(messageRequest);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getRecipientId()),
                "/queue/messages",
                ChatNotification.builder()
                        .id(message.getId())
                        .recipientId(message.getRecipientId())
                        .senderId(message.getSenderId())
                        .message(message.getMessage())
                        .time(message.getTime().toString())
                        .build()
        );
    }

    @PostMapping("/chat/create")
    public String createChat(@ModelAttribute ChatRequest chatRequest) {
        chatRoomService.getChatById(chatRequest.getSenderId(), chatRequest.getRecipientId(), true);
        return "redirect:/chat";
    }

    @GetMapping("/chats")
    public ResponseEntity<List<ChatResponse>> getAllChatForUser(@AuthenticationPrincipal User user) {
        List<ChatResponse> chatList = chatRoomService.getAllChatForUser(user.getUsername());
        return ResponseEntity.ok(chatList);
    }
}