package com.stormx.hicoder.elastic.repositories;

import com.stormx.hicoder.elastic.entities.PostElastic;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, String> {
    List<PostElastic> findByContentOrUsername(String content, String username);
    @NotNull
    Optional<PostElastic> findById(@NotNull String id);


}
