package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.helpers.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PostService {

    boolean likePostOperation(String postId, User currentUser);

    Page<Post> getAllPostsOfUser(User user, Pageable pageable);

    Post getPostById(String postId);

    PostDTO getUserPostById(String postId, User currentUser);

    PostDTO createPost(PostRequest post, User user);

    PostDTO updatePost(String postId, PostRequest postDetails, User currentUser);

    void deletePost(String postId, User currentUser);

    Page<Post> getPostNewsFeed(User currentUser, PageRequest pageRequest);
}
