package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
    private long likesCount;

    public static CommentDTO fromComment(Comment comment) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(comment, CommentDTO.class);
    }

}
