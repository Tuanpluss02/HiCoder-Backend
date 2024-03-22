package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.NewCommentRequest;
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

@RestController()
@RequestMapping(path = "api/v1/comment")
@CrossOrigin(origins = "*")
@Tag(name = "User Comment Controller", description = "Include method to manage user's comment")
@RequiredArgsConstructor
public class CommentController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> createNewComment(@Valid @RequestBody NewCommentRequest newCommentRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postToComment = postService.getPostById(newCommentRequest.getPostId());
        CommentDTO response = commentService.createComment(newCommentRequest, currentUser, postToComment);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED, "Create new comment successfully", request.getRequestURI(), response));
    }
}
