package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.helpers.CommentRequest;
import com.stormx.hicoder.dto.CommentDTO;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    boolean likeCommentOperation(String commentId, User currentUser);

    Page<Comment> getAllCommentsOfPost(Post post, Pageable pageable);

    Comment getCommentById(String commentId);

    CommentDTO createComment(CommentRequest commentRequest, User user, Post post);

    CommentDTO updateComment(String commentId, CommentRequest commentRequest, User currentUser, Post postContainThisComment);

    void deleteComment(String commentId, User currentUser, Post post);
}
