package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.requests.NewPostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.PostRepository;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<PostDTO> getAllPostsOfUser(User user) {
        List<Post> userPosts = postRepository.findAllByAuthor(user);
        return userPosts.stream()
                .map(PostDTO::new)
                .toList();
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
    public PostDTO createPost(NewPostRequest newPostRequest, User user) {
        Post newPost = new Post(newPostRequest);
        newPost.setAuthor(user);
        user.addPost(newPost);
        userRepository.save(user);
        postRepository.save(newPost);
        return new PostDTO(newPost);
    }


    @Override
    public PostDTO updatePost(String postId, NewPostRequest postDetails, User currentUser) {
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

}
