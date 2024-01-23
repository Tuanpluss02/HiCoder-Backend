package com.stormx.hicoder.interfaces;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    PostDTO getPostById(String postId);

    Post createPost(Post post);

    Post updatePost(String postId, Post postDetails);

    boolean deletePost(String postId);
}
