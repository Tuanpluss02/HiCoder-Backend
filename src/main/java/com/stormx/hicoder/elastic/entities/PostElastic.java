package com.stormx.hicoder.elastic.entities;
import com.stormx.hicoder.dto.PostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "posts")
@NoArgsConstructor
public class PostElastic {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Text, name = "content")
    private String content;
    @Field(type = FieldType.Text, name = "author")
    private String author;
    private String createdAt;
    private String mediaUrl;
    private long likesCount;
    private long commentsCount;

    public static PostElastic fromPostDTO(PostDTO post) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(post, PostElastic.class);
    }
}