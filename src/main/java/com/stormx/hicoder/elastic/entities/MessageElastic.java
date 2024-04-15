package com.stormx.hicoder.elastic.entities;


import com.stormx.hicoder.dto.MessageDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    private String conversation;
    private String sendAt;
    private String updatedAt;

    public static MessageElastic fromMessageDTO(MessageDTO messageDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(messageDTO, MessageElastic.class);
    }
}
