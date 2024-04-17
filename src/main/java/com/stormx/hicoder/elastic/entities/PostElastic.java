package com.stormx.hicoder.elastic.entities;
import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
    private String createdAt;
    private String mediaUrl;
    private boolean isLiked;
    private long likesCount;
    private long commentsCount;

    private String author;
    private String username;
    private String avatarUrl;

    public static PostElastic fromPostDTO(PostDTO post) {
        PostElastic postElastic = new PostElastic();
        postElastic.setId(post.getId());
        postElastic.setTitle(post.getTitle());
        postElastic.setContent(post.getContent());
        postElastic.setCreatedAt(post.getCreatedAt());
        postElastic.setMediaUrl(post.getMediaUrl());
        postElastic.setLiked(post.isLiked());
        postElastic.setLikesCount(post.getLikesCount());
        postElastic.setCommentsCount(post.getCommentsCount());
        postElastic.setAuthor(post.getAuthor().getId());
        postElastic.setUsername(post.getAuthor().getUsername());
        postElastic.setAvatarUrl(post.getAuthor().getAvatarUrl());
        return postElastic;
    }
}
