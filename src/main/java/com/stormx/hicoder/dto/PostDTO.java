package com.stormx.hicoder.dto;

import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public static PostDTO fromPost(Post post) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(post, PostDTO.class);
    }
    public static PostDTO fromPostElastic(PostElastic postElastic) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(postElastic, PostDTO.class);
    }
}
