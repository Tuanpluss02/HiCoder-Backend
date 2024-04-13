package com.stormx.hicoder.elastic.services;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.elastic.entities.PostElastic;

import java.util.List;

public interface PostElasticService {
    List<PostElastic> searchPosts(String keyword);
    void addPost(PostDTO post);
    void deletePost(String postId);
}
