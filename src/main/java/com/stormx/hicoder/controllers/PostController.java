package com.stormx.hicoder.controllers;

import com.stormx.hicoder.dto.NewPostDTO;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/me")
    public List<Post> getCurrentUserPosts() {
        UserDTO currentUserDTO = userService.getCurrentUser();
        User currentUser = User.builder()
                .id(currentUserDTO.getId())
                .username(currentUserDTO.getUsername())
                .email(currentUserDTO.getEmail())
                .role(currentUserDTO.getRole())
                .build();
        return postService.getAllPostsOfUser(currentUser);
    }

    @PostMapping("/new")
    public Post newPost(@Valid NewPostDTO newPostDTO){
        UserDTO currentUserDTO = userService.getCurrentUser();
        User currentUser = User.builder()
                .id(currentUserDTO.getId())
                .username(currentUserDTO.getUsername())
                .email(currentUserDTO.getEmail())
                .role(currentUserDTO.getRole())
                .build();
        return postService.createPost(newPostDTO, currentUser);
    }
}
