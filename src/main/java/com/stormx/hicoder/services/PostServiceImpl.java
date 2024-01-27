package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.repositories.PostRepository;
import com.stormx.hicoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPostsOfUser(User user) {
        return postRepository.findAllByUser(user);
    }

    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new BadRequestException("Post not found id: " + postId));
    }

    @Override
    public Post createPost(PostDTO post, User user) {
        Post newPost = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .user(user)
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public Post updatePost(String postId, PostDTO postDetails) {
        Post post = getPostById(postId);
        if (post != null) {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public boolean deletePost(String postId) {
        Post post = getPostById(postId);
        if (post != null) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

}
