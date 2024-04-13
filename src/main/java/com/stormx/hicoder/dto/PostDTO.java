package com.stormx.hicoder.dto;

import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String id;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String mediaUrl;
    private long likesCount;
    private long commentsCount;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getId();
        this.likesCount = post.getLikedByUsers().size();
        this.commentsCount = post.getComments().size();
        this.createdAt = post.getCreatedAt().toString();
        this.mediaUrl = post.getMediaUrl();
    }

    public PostDTO(PostElastic post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.likesCount = post.getLikesCount();
        this.commentsCount = post.getCommentsCount();
        this.createdAt = post.getCreatedAt();
        this.mediaUrl = post.getMediaUrl();
    }
}
