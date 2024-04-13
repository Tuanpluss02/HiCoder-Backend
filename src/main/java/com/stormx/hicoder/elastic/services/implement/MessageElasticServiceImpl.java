package com.stormx.hicoder.elastic.services.implement;

import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.elastic.entities.MessageElastic;
import com.stormx.hicoder.elastic.repositories.MessageElasticRepository;
import com.stormx.hicoder.elastic.services.MessageElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageElasticServiceImpl implements MessageElasticService {
    private final MessageElasticRepository messageRepository;

    @Override
    public List<MessageElastic> searchMessages(String keyword) {
        return messageRepository.findByContent(keyword);
    }

    @Override
    public  List<MessageElastic> searchMessages(String sender, String content) {
        return messageRepository.findBySenderAndAndContent(sender, content);
    }
    @Override
    public List<MessageElastic> searchMessages(String sender, String receiver, String content) {
        return messageRepository.findBySenderAndReceiverAndContent(sender, receiver, content);
    }

    @Override
    public void addMessage(MessageDTO message) {
        MessageElastic messageElastic = MessageElastic.fromMessageDTO(message);
        messageRepository.save(messageElastic);
    }

    @Override
    public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
    }
}
