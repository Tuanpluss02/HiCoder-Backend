package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.modelmapper.ModelMapper;

public class ConversationDTO {
    private String id;
    private String sender;
    private String receiver;
    private MessageDTO lastMessage;

    public static ConversationDTO fromConversation(Conversation conversation) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(conversation, ConversationDTO.class);
    }
}
