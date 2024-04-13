package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByAuthor(User author, Pageable pageable);

    Page<Post> findAllByAuthorInOrderByCreatedAtDesc(List<User> followings, PageRequest pageRequest);
}
