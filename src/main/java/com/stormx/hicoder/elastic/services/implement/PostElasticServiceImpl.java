package com.stormx.hicoder.elastic.services.implement;

import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.elastic.repositories.PostElasticRepository;
import com.stormx.hicoder.elastic.services.PostElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostElasticServiceImpl implements PostElasticService {
    private final PostElasticRepository postRepository;

    @Override
    public List<PostElastic> searchPosts(String keyword) {
        return postRepository.findByTitleOrContent(keyword, keyword);
    }

    @Override
    public void addPost(PostDTO post) {
        PostElastic postElastic = new PostElastic(post);
        postRepository.save(postElastic);
    }

    @Override
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }
}
