package com.stormx.hicoder.elastic.repositories;

import com.stormx.hicoder.elastic.entities.MessageElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MessageElasticRepository extends ElasticsearchRepository<MessageElastic, String> {
    List<MessageElastic> findByContent(String content);
    List<MessageElastic> findBySenderAndAndContent(String sender, String content);
    List<MessageElastic> findBySenderAndReceiverAndContent(String sender, String receiver, String content);
}
