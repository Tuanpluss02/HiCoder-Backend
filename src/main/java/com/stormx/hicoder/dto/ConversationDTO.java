package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
@Setter
@Getter
@Data
public class ConversationDTO {
    private String id;
    private String sender;
    private String receiver;
    private MessageDTO lastMessage;

    public static ConversationDTO fromConversation(Conversation conversation) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Conversation, ConversationDTO>() {
            @Override
            protected void configure() {
                map().setSender(source.getSender().getId());
                map().setReceiver(source.getReceiver().getId());
            }
        });

        return modelMapper.map(conversation, ConversationDTO.class);
    }
}
