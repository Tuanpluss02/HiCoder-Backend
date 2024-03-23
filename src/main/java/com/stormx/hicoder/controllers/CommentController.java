package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.CommentRequest;
import com.stormx.hicoder.dto.CommentDTO;
import com.stormx.hicoder.entities.Comment;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.CommentService;
import com.stormx.hicoder.services.PostService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.stormx.hicoder.common.Utils.calculatePageable;
import static com.stormx.hicoder.common.Utils.extractToDTO;

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
    public ResponseEntity<SuccessResponse> createNewComment(@PathVariable String postId,
                                                            @Valid @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postToComment = postService.getPostById(postId);
        CommentDTO response = commentService.createComment(commentRequest, currentUser, postToComment);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED,
                "Create new comment successfully", request.getRequestURI(), response));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable String postId, @PathVariable String commentId,
                                                         @Valid @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postHaveThisComment = postService.getPostById(postId);
        CommentDTO response = commentService.updateComment(commentId, commentRequest, currentUser, postHaveThisComment);
        return ResponseEntity.ok(
                new SuccessResponse(HttpStatus.OK, "Update comment successfully", request.getRequestURI(), response));
    }

    @GetMapping()
    public ResponseEntity<ResponseGeneral> getPostComment(@PathVariable String postId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "createdAt,desc") String[] sort,
                                                          HttpServletRequest request) {
        PageRequest pageRequest = calculatePageable(page, size, sort, CommentDTO.class, request);
        Post postToGetComment = postService.getPostById(postId);
        Page<Comment> commentPage = commentService.getAllCommentsOfPost(postToGetComment, pageRequest);
        Pair<PaginationInfo, List<CommentDTO>> response = extractToDTO(commentPage, CommentDTO::new);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get comments for post successfully",
                request.getRequestURI(), response.getLeft(), response.getRight()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> deleteComment(@PathVariable String postId, @PathVariable String commentId,
                                                         HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post postHaveThisComment = postService.getPostById(postId);
        commentService.deleteComment(commentId, currentUser, postHaveThisComment);
        return ResponseEntity.accepted()
                .body(new SuccessResponse(HttpStatus.OK, "Remove comment successfully", request.getRequestURI(), null));
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<SuccessResponse> likeComment(@PathVariable String commentId, @PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        boolean response = commentService.likeCommentOperation(commentId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, (response ? "Like" : "Unlike") + " comment successfully", request.getRequestURI(), null));

    }
}
