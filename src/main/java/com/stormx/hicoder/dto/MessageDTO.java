package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDTO {
    private User sender;
    private String content;
    private User receiver;

    public MessageDTO(User sender, String content, User receiver) {
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }
}
