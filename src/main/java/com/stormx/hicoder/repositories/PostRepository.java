package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByAuthor(User author, Pageable pageable);
}
