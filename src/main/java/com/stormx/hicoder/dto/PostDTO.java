package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Post;
import lombok.Data;

@Data
public class PostDTO {

    private String id;

    private String title;

    private String content;

    private String author;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getId();
    }
}
