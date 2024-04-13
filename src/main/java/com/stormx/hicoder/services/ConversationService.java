package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.ConversationDTO;
import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface ConversationService {
    List<ConversationDTO> getConversations(User user);
    void createConversation(User sender, User receiver);
    void deleteConversation(String conversationId, User user);
}
