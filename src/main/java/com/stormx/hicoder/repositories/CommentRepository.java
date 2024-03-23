package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
