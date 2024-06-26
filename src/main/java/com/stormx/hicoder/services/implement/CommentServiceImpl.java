package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.controllers.helpers.CommentRequest;
import com.stormx.hicoder.dto.CommentDTO;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.CommentRepository;
import com.stormx.hicoder.repositories.PostRepository;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public boolean likeCommentOperation(String commentId, User currentUser) {
        Comment comment = getCommentById(commentId);
        boolean res = comment.likeOperation(currentUser);
        commentRepository.save(comment);
        return res;
    }

    @Override
    public Page<Comment> getAllCommentsOfPost(Post post, Pageable pageable) {
        return commentRepository.findAllByPost(post, pageable);
    }

    @Override
    public Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("Comment not found id: " + commentId));
    }

    @Override
    public CommentDTO createComment(CommentRequest commentRequest, User user, Post post) {
        Comment newComment = new Comment(commentRequest);
        newComment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newComment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newComment.setAuthor(user);
        newComment.setPost(post);
        post.addComment(newComment);
        user.addComment(newComment);
        commentRepository.save(newComment);
        postRepository.save(post);
        userRepository.save(user);
        return CommentDTO.fromComment(newComment);
    }

    @Override
    public CommentDTO updateComment(String commentId, CommentRequest commentRequest, User currentUser, Post post) {
        Comment commentToUpdate = checkCommentValid(commentId, currentUser, post);
        commentToUpdate.setContent(commentRequest.getContent());
        commentToUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        commentRepository.save(commentToUpdate);
        return CommentDTO.fromComment(commentToUpdate);
    }


    @Override
    public void deleteComment(String commentId, User currentUser, Post post) {
        Comment commentToDelete = checkCommentValid(commentId, currentUser, post);
        currentUser.removeComment(commentToDelete);
        post.removeComment(commentToDelete);
        commentRepository.delete(commentToDelete);
        postRepository.save(post);
        userRepository.save(currentUser);
    }


    private Comment checkCommentValid(String commentId, User currentUser, Post post) {
        Comment commentData = getCommentById(commentId);
        if (!commentData.isCommentedBy(currentUser)) {
            throw new BadRequestException("User doesn't have comment: " + commentId + " in post: " + post.getId());
        }
        if (!commentData.isCommentedOn(post)) {
            throw new BadRequestException("Post: " + post.getId() + " doesn't have comment: " + commentId);
        }
        return commentData;
    }
}
