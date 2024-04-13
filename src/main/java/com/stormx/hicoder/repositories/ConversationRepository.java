package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Conversation;
import com.stormx.hicoder.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    List<Conversation> findAllBySender(User sender);
}
