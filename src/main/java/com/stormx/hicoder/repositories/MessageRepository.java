package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    Page<Message> findAllBySenderAndReceiver(User sender, User receiver, Pageable pageable);
}
