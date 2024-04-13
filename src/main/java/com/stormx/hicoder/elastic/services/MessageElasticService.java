package com.stormx.hicoder.elastic.services;

import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.elastic.entities.MessageElastic;

import java.util.List;

public interface MessageElasticService {
    List<MessageElastic> searchMessages(String keyword);

    List<MessageElastic> searchMessages(String sender, String content);

    List<MessageElastic> searchMessages(String sender, String receiver, String content);

    void addMessage(MessageDTO message);
    void deleteMessage(String messageId);
}
