package com.stormx.hicoder.controllers;

import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "api/v1/chat",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}
)
public class MessageController {
    @Autowired
    private MessageService messageService;
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    @PostMapping()
    public Message sendMessage(@Payload Message message) {
        return this.messageService.sendMessage(message);
    }
}
