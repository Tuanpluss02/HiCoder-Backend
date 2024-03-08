package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.requests.NewPostRequest;
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
    public List<Post> getAllPostsOfUser(User user) {
        return postRepository.findAllByUser(user);
    }

    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new BadRequestException("Post not found id: " + postId));
    }

    @Override
    public Post createPost(NewPostRequest post, User user) {
        Post newPost = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(user)
                .build();

        return postRepository.save(newPost);
    }


    @Override
    public Post updatePost(String postId, NewPostRequest postDetails) {
        Post post = getPostById(postId);
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    @Override
    public boolean deletePost(String postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
        return true;
    }

}
