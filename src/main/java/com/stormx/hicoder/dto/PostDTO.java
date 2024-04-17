package com.stormx.hicoder.dto;

import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String id;
    private String content;
    private UserDTO author;
    private String createdAt;
    private String mediaUrl;
    private boolean isLiked;
    private long likesCount;
    private long commentsCount;

    public static PostDTO fromPost(Post post) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Post, PostDTO>() {
            @Override
            protected void configure() {
                map().setAuthor(UserDTO.fromUser(source.getAuthor()));
                map().setLiked(false);
            }
        });
        return modelMapper.map(post, PostDTO.class);
    }
    public static PostDTO fromPostElastic(PostElastic postElastic) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<PostElastic, PostDTO>() {
            @Override
            protected void configure() {
                map().setAuthor(null);
            }
        });
        return modelMapper.map(postElastic, PostDTO.class);
    }
}
