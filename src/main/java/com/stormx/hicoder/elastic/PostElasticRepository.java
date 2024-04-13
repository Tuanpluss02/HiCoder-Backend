package com.stormx.hicoder.elastic;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, String> {
    List<PostElastic> findByTitleOrContent(String title, String content);
    @NotNull
    Optional<PostElastic> findById(@NotNull String id);


}
