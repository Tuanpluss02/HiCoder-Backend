package com.stormx.hicoder.controllers;


import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.ConversationDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.ConversationService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/conversation")
@Tag(name = "Conversation Controller", description = "Include method to manage user's conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;
    private final UserService userService;
    @GetMapping("/get")
    public ResponseEntity<?> getConversations(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        List<ConversationDTO> conversations = conversationService.getConversations(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get conversations successfully", request.getRequestURI(), conversations));
    }

    @GetMapping("/create")
    public ResponseEntity<?> createConversation(String receiverUsername, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        User receiver = userService.loadUserByUsername(receiverUsername);
        conversationService.createConversation(currentUser, receiver);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Create conversation successfully", request.getRequestURI()));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteConversation(String conversationId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        conversationService.deleteConversation(conversationId, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Delete conversation successfully", request.getRequestURI()));
    }
}
