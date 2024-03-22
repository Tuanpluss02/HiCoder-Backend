package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findAllByPost(Post post);
}
