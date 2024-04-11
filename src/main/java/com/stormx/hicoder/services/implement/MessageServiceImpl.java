package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.requests.MessageEdit;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.MessageRepository;
import com.stormx.hicoder.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;


    @Override
    public void saveMessage(MessageDTO message) {
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setSender(message.getSender());
        newMessage.setReceiver(message.getReceiver());
        newMessage.setSendAt(Timestamp.valueOf(LocalDateTime.now()));
        newMessage.setEditedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageRepository.save(newMessage);
    }

    @Override
    public void deleteMessage(User currentUser,String messageId) {
        Message newMessage = messageRepository.findById(messageId).orElseThrow(() -> new BadRequestException("Message not found id: " + messageId));
        if(!newMessage.isSendBy(currentUser)) {
            throw new BadRequestException("You are not the sender of this message");
        }
        messageRepository.delete(newMessage);
    }

    @Override
    public void editMessage(User currentUser, MessageEdit message) {
        Message newMessage = messageRepository.findById(message.getMessageId()).orElseThrow(() -> new BadRequestException("Message not found id: " + message.getMessageId()));
        if(!newMessage.isSendBy(currentUser)) {
            throw new BadRequestException("You are not the sender of this message");
        }
        newMessage.setContent(message.getNewContent());
        newMessage.setEditedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageRepository.save(newMessage);
    }
}