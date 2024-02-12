package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.entities.Post;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.services.PostService;
import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/post",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}
)
//@CrossOrigin(origins = "*")
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
    public ResponseEntity<ResponseGeneral> getCurrentUserPosts(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<Post> userPosts = postService.getAllPostsOfUser(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user's posts successfully", request.getRequestURI(), userPosts));
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> newPost(@Valid PostDTO postDTO, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Create new post successfully", request.getRequestURI(), postService.createPost(postDTO, currentUser)));
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<SuccessResponse> updatePost(@PathVariable String postId, @Valid PostDTO postDTO, HttpServletRequest request, BindingResult bindingResult) {
        checkValidRequest(bindingResult);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update post successfully", request.getRequestURI(), postService.updatePost(postId, postDTO)));
    }
}
