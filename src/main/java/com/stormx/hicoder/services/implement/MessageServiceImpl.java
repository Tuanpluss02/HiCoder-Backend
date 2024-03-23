package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.repositories.MessageRepository;
import com.stormx.hicoder.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message message) {
        return message;
    }
}
