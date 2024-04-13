package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.helpers.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.PostRepository;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.FollowService;
import com.stormx.hicoder.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowService followService;

    @Override
    public boolean likePostOperation(String postId, User currentUser) {
        Post post = getPostById(postId);
        boolean result = post.likeOperation(currentUser);
        postRepository.save(post);
        return result;
    }

    @Override
    public Page<Post> getAllPostsOfUser(User user, Pageable pageable) {
        return postRepository.findAllByAuthor(user, pageable);
    }


    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new BadRequestException("Post not found id: " + postId));
    }


    @Override
    public PostDTO getUserPostById(String postId, User currentUser) {
        Post userPost = getPostById(postId);
        if (!userPost.isPostedBy(currentUser)) {
            throw new BadRequestException("User doesn't have post: " + postId);
        }
        return new PostDTO(userPost);
    }

    @Override
    public PostDTO createPost(PostRequest postRequest, User user) {
        Post newPost = new Post(postRequest);
        newPost.setAuthor(user);
        newPost.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.addPost(newPost);
        userRepository.save(user);
        postRepository.save(newPost);
        return new PostDTO(newPost);
    }


    @Override
    public PostDTO updatePost(String postId, PostRequest postDetails, User currentUser) {
        Post userPosts = getPostById(postId);
        if (!userPosts.isPostedBy(currentUser)) {
            throw new BadRequestException("User doesn't have post: " + postId);
        }
        userPosts.setTitle(postDetails.getTitle());
        userPosts.setContent(postDetails.getContent());
        postRepository.save(userPosts);
        return new PostDTO(userPosts);
    }

    @Override
    public void deletePost(String postId, User currentUser) {
        Post post = getPostById(postId);
        if (!post.isPostedBy(currentUser)) {
            throw new BadRequestException("User doesn't have post: " + postId);
        }
        currentUser.removePost(post);
        userRepository.save(currentUser);
        postRepository.delete(post);
    }

    @Override
    public Page<Post> getPostNewsFeed(User currentUser, PageRequest pageRequest) {
        List<User> followings = followService.getAllFollowings(currentUser, PageRequest.of(0, 100)).getContent();
        return postRepository.findAllByAuthorInOrderByCreatedAtDesc(followings, pageRequest);
    }

}
