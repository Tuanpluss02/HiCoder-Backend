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
    public void createPost(NewPostRequest newPostRequest, User user) {
        Post newPost = new Post(newPostRequest);
        newPost.setAuthor(user);
        user.addPost(newPost);
        userRepository.save(user);
        postRepository.save(newPost);
    }


    @Override
    public void updatePost(String postId, NewPostRequest postDetails, User currentUser) {
        Post oldPost = getPostById(postId);
        if (!oldPost.isPostedBy(currentUser)) {
            throw new BadRequestException("User doesn't have post: " + postId);
        }
        postRepository.save(oldPost);
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
