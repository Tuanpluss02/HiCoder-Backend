package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.NewPostRequest;
import com.stormx.hicoder.entities.Post;
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
    public ResponseEntity<ResponseGeneral> getCurrentUserPosts(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<Post> userPosts = postService.getAllPostsOfUser(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), userPosts));
    }

    @PostMapping()
    public ResponseEntity<?> newPost(@Valid @RequestBody NewPostRequest newPostRequest, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        Post newPost = postService.createPost(newPostRequest, currentUser);
        return ResponseEntity.created(URI.create(request.getRequestURI())).body(new SuccessResponse(HttpStatus.CREATED, "Create new post successfully", request.getRequestURI(), null));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<SuccessResponse> updatePost(@PathVariable String postId, @Valid NewPostRequest newPostRequest, HttpServletRequest request) {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update post successfully", request.getRequestURI(), postService.updatePost(postId, newPostRequest)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable String postId, HttpServletRequest request) {
        postService.deletePost(postId);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Delete post successfully", request.getRequestURI(), null));
    }
}
