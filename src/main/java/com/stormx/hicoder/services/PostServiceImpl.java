package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.repositories.PostRepository;
import com.stormx.hicoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll();
    }

    //    @Override
//    public List
    @Override
    public PostDTO getPostById(String postId) {
        Optional<PostDTO> postOptional = Optional.of(new PostDTO(postRepository.findById(postId)));
        return postOptional.orElse(null);
    }

    @Override
    public PostDTO createPost(PostDTO post) {
        return postRepository.save(post);
    }

    @Override
    public PostDTO updatePost(String postId, PostDTO postDetails) {
        PostDTO post = getPostById(postId);
        if (post != null) {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            return postRepository.save(post);
        }
        return null;
    }

    @Override
    public boolean deletePost(String postId) {
        PostDTO post = getPostById(postId);
        if (post != null) {
            postRepository.delete(post);
            return true;
        }
        return false;
    }

}
