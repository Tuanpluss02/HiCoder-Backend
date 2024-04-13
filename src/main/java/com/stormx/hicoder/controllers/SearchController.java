package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.dto.PostDTO;
import com.stormx.hicoder.elastic.entities.MessageElastic;
import com.stormx.hicoder.elastic.entities.PostElastic;
import com.stormx.hicoder.elastic.services.MessageElasticService;
import com.stormx.hicoder.elastic.services.PostElasticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/posts")
    public ResponseEntity<?> searchPosts(@RequestParam String keyword, HttpServletRequest request) {
        List<PostElastic> searchElastic =  postElasticService.searchPosts(keyword);
        List<PostDTO> searchPosts = searchElastic.stream().map(PostDTO::fromPostElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search posts successfully", request.getRequestURI(), searchPosts));
    }

    @GetMapping("/messages")
    public ResponseEntity<?> searchMessages(@RequestParam String keyword, HttpServletRequest request) {
        List<MessageElastic> searchElastic =  messageElasticService.searchMessages(keyword);
        List<MessageDTO> searchMessages = searchElastic.stream().map(MessageDTO::fromMessageElastic).toList();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Search messages successfully", request.getRequestURI(), searchMessages));
    }
}
