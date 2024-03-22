package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.CommentRequest;
import com.stormx.hicoder.dto.CommentDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.CommentService;
import com.stormx.hicoder.services.PostService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/post/{postId}/comment")
@CrossOrigin(origins = "*")
@Tag(name = "User Comment Controller", description = "Include method to manage user's comment")
@RequiredArgsConstructor
public class CommentController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> createNewComment(@PathVariable String postId, @Valid @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postToComment = postService.getPostById(postId);
        CommentDTO response = commentService.createComment(commentRequest, currentUser, postToComment);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED, "Create new comment successfully", request.getRequestURI(), response));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> updatePost(@PathVariable String postId, @PathVariable String commentId, @Valid @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postHaveThisComment = postService.getPostById(postId);
        CommentDTO response = commentService.updateComment(commentId, commentRequest, currentUser, postHaveThisComment);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update comment successfully", request.getRequestURI(), response));
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> getPostComment(@PathVariable String postId, HttpServletRequest request) {
        Post postToGetComment = postService.getPostById(postId);
        List<CommentDTO> allComment = commentService.getAllCommentsOfPost(postToGetComment);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get comments for post successfully", request.getRequestURI(), allComment));

    }
}
