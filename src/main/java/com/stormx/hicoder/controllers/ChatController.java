package com.stormx.hicoder.controllers;

import com.stormx.hicoder.controllers.helpers.MessageSend;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.ConversationService;
import com.stormx.hicoder.services.MessageService;
import com.stormx.hicoder.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserService userService;

    @MessageMapping("/send")
    public void chat(@Payload MessageSend message) {
        try {
            MessageDTO messageDTO = new MessageDTO(message.getSender(), message.getContent(), message.getReceiver());
            User sender = userService.loadUserByUsername(message.getSender());
            User receiver = userService.loadUserByUsername(message.getReceiver());
            Conversation conv = conversationService.createConversation(sender, receiver);
            messageService.saveMessage(messageDTO, conv);
            simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send message");
        }
    }
}