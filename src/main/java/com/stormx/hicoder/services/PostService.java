package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface PostService {

    boolean likePostOperation(String postId, User currentUser);

    List<PostDTO> getAllPostsOfUser(User user);

    Post getPostById(String postId);

    PostDTO getUserPostById(String postId, User currentUser);

    PostDTO createPost(PostRequest post, User user);

    PostDTO updatePost(String postId, PostRequest postDetails, User currentUser);

    void deletePost(String postId, User currentUser);
}
