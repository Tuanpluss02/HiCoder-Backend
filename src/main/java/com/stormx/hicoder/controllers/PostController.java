package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.interfaces.ResponseGeneral;
import com.stormx.hicoder.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/post",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/me")
    public ResponseEntity<ResponseGeneral> getCurrentUserPosts(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<Post> userPosts = postService.getAllPostsOfUser(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", request.getRequestURI(), userPosts));
    }

//    @PostMapping("/new")
//    public Post newPost(@Valid )
}
