package com.stormx.hicoder.dto;

import com.stormx.hicoder.elastic.entities.MessageElastic;
import com.stormx.hicoder.entities.Message;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Setter
@Getter
@Data
@NoArgsConstructor
public class MessageDTO {
    private String id;
    private String sender;
    private String content;
    private String receiver;
    private String conversation;
    private String sendAt;
    private String updatedAt;

    public MessageDTO(String sender, String content, String receiver) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }

    public static MessageDTO fromMessage(Message message) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Message, MessageDTO>() {
            @Override
            protected void configure() {
                map().setReceiver(source.getReceiver().getId());
                map().setSender(source.getSender().getId());
                map().setConversation(source.getConversation().getId());
            }
        });
        return modelMapper.map(message, MessageDTO.class);
    }

    public static MessageDTO fromMessageElastic(MessageElastic messageElastic) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(messageElastic, MessageDTO.class);
    }
}
