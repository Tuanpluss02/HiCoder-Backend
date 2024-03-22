package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.NewPostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface PostService {

    void likePostOperation(String postId, User currentUser);

    List<PostDTO> getAllPostsOfUser(User user);

    Post getPostById(String postId);

    PostDTO getUserPostById(String postId, User currentUser);

    PostDTO createPost(NewPostRequest post, User user);

    PostDTO updatePost(String postId, NewPostRequest postDetails, User currentUser);

    void deletePost(String postId, User currentUser);
}
