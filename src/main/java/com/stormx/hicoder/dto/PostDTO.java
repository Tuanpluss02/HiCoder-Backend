package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Post;
import lombok.Data;

@Data
public class PostDTO {

    private String id;

    private String title;

    private String content;

    private String author;

    private String createdAt;

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
    }
}
