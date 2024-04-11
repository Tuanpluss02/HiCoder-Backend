package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.MessageEdit;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;

public interface MessageService {
    public void saveMessage(MessageDTO message);

    void deleteMessage(User currentUser, String messageId);

    void editMessage(User currentUser, MessageEdit message);
}
