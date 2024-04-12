package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.helpers.MessageEdit;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.MessageRepository;
import com.stormx.hicoder.services.MessageService;
import com.stormx.hicoder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Override
    public void saveMessage(MessageDTO message) {
        User currentUser = userService.loadUserByUsername(message.getSender());
        User receiver = userService.loadUserByUsername(message.getReceiver());
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        newMessage.setSender(currentUser);
        newMessage.setReceiver(receiver);
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

    @Override
    public Page<Message> getMessages(User currentUser, User receiver, Pageable pageable) {
        return messageRepository.findAllBySenderAndReceiver(currentUser, receiver, pageable);
    }
}