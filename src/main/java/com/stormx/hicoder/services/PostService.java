package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.NewPostRequest;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface PostService {
    List<Post> getAllPostsOfUser(User user);

    Post getPostById(String postId);

    void createPost(NewPostRequest post, User user);

    void updatePost(String postId, NewPostRequest postDetails, User currentUser);

    void deletePost(String postId, User currentUser);
}
