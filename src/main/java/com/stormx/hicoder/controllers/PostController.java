package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.NewPostDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.interfaces.PostService;
import com.stormx.hicoder.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/post",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
public class PostController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    private static void checkValidRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.getFirst());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserPosts(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's post detail successfully", request.getRequestURI(), postService.getAllPostsOfUser(currentUser)));
    }

//    @PatchMapping("/update")
//    public ResponseEntity<?> updatePost(){
//        return null;
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewPost(@Valid NewPostDTO newPostDTO, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Create new post successfully", request.getRequestURI(), postService.createPost(newPostDTO, currentUser)));
    }
}
