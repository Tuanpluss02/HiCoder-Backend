package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.helpers.MessageEdit;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    void saveMessage(MessageDTO message);
    void saveMessage(MessageDTO message, Conversation conv);
    void deleteMessage(User currentUser, String messageId);

    void editMessage(User currentUser, MessageEdit message);

    Page<Message> getMessages(User currentUser, User receiver, Pageable pageable);
}
