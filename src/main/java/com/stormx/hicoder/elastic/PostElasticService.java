package com.stormx.hicoder.elastic;

import com.stormx.hicoder.controllers.helpers.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface PostElasticService {
    List<PostElastic> searchPosts(String keyword);
    void addPost(PostDTO post);

    void deletePost(String postId);
}
