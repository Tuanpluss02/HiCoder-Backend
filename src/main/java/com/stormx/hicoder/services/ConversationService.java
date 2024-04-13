package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface ConversationService {
    List<Conversation> getConversations(User user);
    void createConversation(User sender, User receiver);
    void deleteConversation(String conversationId);
}
