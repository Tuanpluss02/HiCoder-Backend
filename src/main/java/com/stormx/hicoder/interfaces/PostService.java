package com.stormx.hicoder.interfaces;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface PostService {
    List<Post> getAllPostsOfUser(User user);

    Post getPostById(String postId);

    Post createPost(PostDTO post, User user);

    Post updatePost(String postId, PostDTO postDetails);

    boolean deletePost(String postId);
}
