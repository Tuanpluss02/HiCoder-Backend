package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
    private long likesCount;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor().getId();
        this.likesCount = comment.getLikedByUsers().size();
        this.updatedAt = comment.getUpdatedAt().toString();
        this.createdAt = comment.getCreatedAt().toString();
    }

}
