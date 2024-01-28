package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.NewPostDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.interfaces.ResponseGeneral;
import com.stormx.hicoder.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/me")
    public ResponseEntity<ResponseGeneral> getCurrentUserPosts() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", postService.getAllPostsOfUser(currentUser)));
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseGeneral> newPost(@Valid NewPostDTO newPostDTO) {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Create new post successfully", postService.createPost(newPostDTO, currentUser)));

    }
}
