package com.stormx.hicoder.controllers;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.helpers.PostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.NotificationService;
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
@RequestMapping(path = "api/v1/post")
@CrossOrigin(origins = "*")
@Tag(name = "User Post Controller", description = "Include method to manage user's post")
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final NotificationService notificationService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserPosts(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "createdAt,desc") String[] sort,
                                                 HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PageRequest pageRequest = calculatePageable(page, size, sort, PostDTO.class, request);
        Page<Post> userPosts = postService.getAllPostsOfUser(currentUser, pageRequest);
        Pair<PaginationInfo, List<PostDTO>> response = extractToDTO(userPosts, PostDTO::new);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), response.getLeft(), response.getRight()));
    }

    @GetMapping("/newsfeed")
    public ResponseEntity<ResponseGeneral> getPostComment(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "createdAt,desc") String[] sort,
                                                          HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PageRequest pageRequest = calculatePageable(page, size, sort, PostDTO.class, request);
        Page<Post> postNewsFeed = postService.getPostNewsFeed(currentUser, pageRequest);
        Pair<PaginationInfo, List<PostDTO>> response = extractToDTO(postNewsFeed, PostDTO::new);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get post newsfeed successfully",
                request.getRequestURI(), response.getLeft(), response.getRight()));
    }


    @GetMapping("/{postId}")
    public ResponseEntity<?> getUserPostById(@PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PostDTO userPosts = postService.getUserPostById(postId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), userPosts));
    }

    @PostMapping()
    public ResponseEntity<?> newPost(@Valid @RequestBody PostRequest postRequest, HttpServletRequest request) throws FirebaseMessagingException {
        User currentUser = userService.getCurrentUser();
        PostDTO createdPost = postService.createPost(postRequest, currentUser);
        notificationService.newPostNotification(currentUser);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED, "Create new post successfully", request.getRequestURI(), createdPost));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<SuccessResponse> updatePost(@PathVariable String postId, @Valid @RequestBody PostRequest postRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PostDTO updatedPost = postService.updatePost(postId, postRequest, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update post successfully", request.getRequestURI(), updatedPost));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        postService.deletePost(postId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Delete post successfully", request.getRequestURI(), null));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        boolean response = postService.likePostOperation(postId, currentUser);
        if (response)
            notificationService.newPostLikeNotification(currentUser, postService.getPostById(postId));
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, (response ? "Like" : "Unlike") + " post successfully", request.getRequestURI(), null));
    }
}
