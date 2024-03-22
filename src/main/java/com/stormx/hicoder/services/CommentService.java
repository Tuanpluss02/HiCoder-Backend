package com.stormx.hicoder.services;

import com.stormx.hicoder.controllers.requests.CommentRequest;
import com.stormx.hicoder.dto.CommentDTO;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;

import java.util.List;

public interface CommentService {
    boolean likeCommentOperation(String commentId, User currentUser);

    List<CommentDTO> getAllCommentsOfPost(Post post);

    Comment getCommentById(String commentId);

    CommentDTO getUserCommentById(String commentId, User currentUser);

    CommentDTO createComment(CommentRequest commentRequest, User user, Post post);

    CommentDTO updateComment(String commentId, CommentRequest commentRequest, User currentUser, Post postContainThisComment);

    void deleteComment(String commentId, User currentUser, Post post);
}
