package com.stormx.hicoder.elastic;
import com.stormx.hicoder.controllers.helpers.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    public PostElastic(PostDTO post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.mediaUrl = post.getMediaUrl();
        this.author = post.getAuthor();
        this.createdAt = post.getCreatedAt();
        this.likesCount = post.getLikesCount();
        this.commentsCount = post.getCommentsCount();
    }
}
