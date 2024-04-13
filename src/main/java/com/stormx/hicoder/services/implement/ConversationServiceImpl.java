package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.dto.ConversationDTO;
import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.repositories.ConversationRepository;
import com.stormx.hicoder.services.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    @Override
    public List<ConversationDTO> getConversations(User user) {
        return conversationRepository.findAllBySender(user).stream().map(ConversationDTO::fromConversation).toList();
    }

    @Override
    public void createConversation(User sender, User receiver) {
        Conversation conversation = new Conversation();
        conversation.setSender(sender);
        conversation.setReceiver(receiver);
        conversationRepository.save(conversation);
    }

    @Override
    public void deleteConversation(String conversationId, User user) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new RuntimeException("Conversation not found"));
        if (!conversation.getSender().equals(user)) {
            throw new RuntimeException("You are not the own of this conversation");
        }
        conversationRepository.deleteById(conversationId);

    }
}
