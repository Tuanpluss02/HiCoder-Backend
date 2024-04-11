package com.stormx.hicoder.controllers;

import com.stormx.hicoder.controllers.requests.MessageEdit;
import com.stormx.hicoder.controllers.requests.MessageSend;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.MessageService;
import com.stormx.hicoder.services.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final MessageService messageService;

    @MessageMapping("/send")
    public void chat(@Payload MessageSend message) {
        log.info("Message received: {}", message);
        User currentUser = userService.getCurrentUser();
        User toUser = userService.getUserById(message.getSender());
        MessageDTO messageDTO = new MessageDTO(currentUser, message.getContent(), toUser);
        messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic", message);
    }

    @MessageMapping("/edit")
    public void edit(@Payload MessageEdit message) {
        log.info("Message received: {}", message);
        User currentUser = userService.getCurrentUser();
        messageService.editMessage(currentUser,message);
//        simpMessagingTemplate.convertAndSendToUser(message.to(), "/topic", message);
        simpMessagingTemplate.convertAndSend("/topic", message);
    }

//    @MessageMapping("/delete")
//    public void delete(@Payload MessageEdit message) {
//        log.info("Message received: {}", message);
//        User currentUser = userService.getCurrentUser();
//        messageService.deleteMessage(currentUser, message.getMessageId());
//        simpMessagingTemplate.convertAndSendToUser(message.to(), "/topic", message);
//    }
}