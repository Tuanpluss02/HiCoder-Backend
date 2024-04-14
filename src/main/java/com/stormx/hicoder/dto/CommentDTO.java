package com.stormx.hicoder.dto;

import com.stormx.hicoder.entities.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
        modelMapper.addMappings(new PropertyMap<Comment, CommentDTO>() {
            @Override
            protected void configure() {
                map().setAuthor(source.getAuthor().getId());
            }
        });

        return modelMapper.map(comment, CommentDTO.class);
    }

}
