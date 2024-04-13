package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Message;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class MessageDTO {
    private String sender;
    private String content;
    private String receiver;
    private String sendAt;
    private String updatedAt;


    public MessageDTO(String sender, String content, String receiver) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }

    public MessageDTO(Message message) {
        this.sender = message.getSender().getId();
        this.content = message.getContent();
        this.receiver = message.getReceiver().getId();
        this.sendAt = message.getSendAt().toString();
        this.updatedAt = message.getEditedAt().toString();
    }
}
