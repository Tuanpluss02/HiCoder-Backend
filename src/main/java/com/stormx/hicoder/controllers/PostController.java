package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.NewPostRequest;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.PostService;
import com.stormx.hicoder.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/post")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserPosts(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<PostDTO> userPosts = postService.getAllPostsOfUser(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), userPosts));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getUserPostById(@PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PostDTO userPosts = postService.getUserPostById(postId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), userPosts));
    }

    @PostMapping()
    public ResponseEntity<?> newPost(@Valid @RequestBody NewPostRequest newPostRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PostDTO createdPost = postService.createPost(newPostRequest, currentUser);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED, "Create new post successfully", request.getRequestURI(), createdPost));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<SuccessResponse> updatePost(@PathVariable String postId, @Valid @RequestBody NewPostRequest newPostRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PostDTO updatedPost = postService.updatePost(postId, newPostRequest, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update post successfully", request.getRequestURI(), updatedPost));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable String postId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        postService.deletePost(postId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Delete post successfully", request.getRequestURI(), null));
    }
}
