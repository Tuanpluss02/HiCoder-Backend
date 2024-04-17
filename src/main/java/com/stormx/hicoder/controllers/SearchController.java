package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.elastic.entities.MessageElastic;
import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.elastic.services.MessageElasticService;
import com.stormx.hicoder.elastic.services.PostElasticService;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/search")
@Tag(name = "Search Controller", description = "Include method to search posts and messages")
@RequiredArgsConstructor
public class SearchController {
    private final PostElasticService postElasticService;
    private final MessageElasticService messageElasticService;
    private final UserService userService;

    @GetMapping("/posts")
    public ResponseEntity<?> searchPosts(@RequestParam String keyword, HttpServletRequest request) {
        List<PostElastic> searchElastic =  postElasticService.searchPosts(keyword);
        List<PostDTO> searchPosts = searchElastic.stream().map(PostDTO::fromPostElastic).toList();
        for (int i = 0; i < searchPosts.size(); i++) {
            User user = userService.getUserById(searchElastic.get(i).getAuthor());
            searchPosts.get(i).setAuthor(UserDTO.fromUser(user));
        }
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search posts successfully", request.getRequestURI(), searchPosts));
    }

    @GetMapping("/messages/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchMessages(@RequestParam String keyword, HttpServletRequest request) {
        List<MessageElastic> searchElastic =  messageElasticService.searchMessages(keyword);
        List<MessageDTO> searchMessages = searchElastic.stream().map(MessageDTO::fromMessageElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search messages successfully", request.getRequestURI(), searchMessages));
    }

    @GetMapping("/messages/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchMessagesAdminOneUser(@RequestParam String sender, @RequestParam String content, HttpServletRequest request) {
        List<MessageElastic> searchElastic =  messageElasticService.searchMessages(sender, content);
        List<MessageDTO> searchMessages = searchElastic.stream().map(MessageDTO::fromMessageElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search messages successfully", request.getRequestURI(), searchMessages));
    }

    @GetMapping("/messages/both")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchMessagesAdminTwoUser(@RequestParam String sender, @RequestParam String receiver, @RequestParam String content, HttpServletRequest request) {
        List<MessageElastic> searchElastic =  messageElasticService.searchMessages(sender, receiver, content);
        List<MessageDTO> searchMessages = searchElastic.stream().map(MessageDTO::fromMessageElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search messages successfully", request.getRequestURI(), searchMessages));
    }

    @GetMapping("/messages/current")
    public ResponseEntity<?> searchMessagesCurrentUser(@RequestParam String receiver, @RequestParam String content, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<MessageElastic> searchElastic =  messageElasticService.searchMessages(currentUser.getId(), receiver, content);
        List<MessageDTO> searchMessages = searchElastic.stream().map(MessageDTO::fromMessageElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search messages successfully", request.getRequestURI(), searchMessages));
    }
}
