package com.stormx.hicoder.repositories;

import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findAllByAuthor(User author);
}
