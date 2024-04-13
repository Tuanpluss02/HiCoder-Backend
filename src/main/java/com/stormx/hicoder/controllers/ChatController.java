package com.stormx.hicoder.controllers;

import com.stormx.hicoder.controllers.helpers.MessageSend;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.services.MessageService;
import com.stormx.hicoder.services.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/send")
    public void chat(@Payload MessageSend message) {
        try {
            log.info("Message received: {}", message);
            MessageDTO messageDTO = new MessageDTO(message.getSender(), message.getContent(), message.getReceiver());
            messageService.saveMessage(messageDTO);
            simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic", message);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }


}