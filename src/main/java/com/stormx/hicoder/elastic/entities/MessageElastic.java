package com.stormx.hicoder.elastic.entities;


import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Timestamp;

@Data
@Document(indexName = "messages")
@NoArgsConstructor
public class MessageElastic {
    @Id
    private String id;
    @Field(name = "content", type = FieldType.Text)
    private String content;
    private String sender;
    private String receiver;
    private String sendAt;
    private String updatedAt;

    public MessageElastic(MessageDTO messageDTO) {
        this.id = messageDTO.getId();
        this.content = messageDTO.getContent();
        this.sender = messageDTO.getSender();
        this.receiver = messageDTO.getReceiver();
        this.sendAt = messageDTO.getSendAt();
        this.updatedAt = messageDTO.getUpdatedAt();
    }
}
