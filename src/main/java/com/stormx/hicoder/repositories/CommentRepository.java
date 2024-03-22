package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
