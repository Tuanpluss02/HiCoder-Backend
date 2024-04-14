package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.helpers.MessageEdit;
import com.stormx.hicoder.dto.MessageDTO;
import com.stormx.hicoder.entities.Message;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.ConversationService;
import com.stormx.hicoder.services.MessageService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stormx.hicoder.common.Utils.calculatePageable;
import static com.stormx.hicoder.common.Utils.extractToDTO;


@RestController
@RequestMapping(path = "api/v1/message")
@Tag(name = "Message Controller", description = "Include method to manage user's message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/get/{receiverUsername}")
    public ResponseEntity<SuccessResponse> getMessages(@PathVariable String receiverUsername,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "sendAt,desc") String[] sort,
                                                       HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        User receiver = userService.loadUserByUsername(receiverUsername);
        PageRequest pageRequest = calculatePageable(page, size, sort, MessageDTO.class);
        Page<Message> messages = messageService.getMessages(currentUser, receiver, pageRequest);
        Pair<PaginationInfo, List<MessageDTO>> response = extractToDTO(messages, MessageDTO::fromMessage);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"Get messages successfully", request.getRequestURI(), response.getLeft(),response.getRight()));
    }

    @PutMapping("/edit")
    public void edit(@Valid @RequestBody MessageEdit message) {
        User currentUser = userService.getCurrentUser();
        messageService.editMessage(currentUser,message);
    }

    @DeleteMapping("/delete")
    public void delete(@Valid @RequestBody MessageEdit message) {
        User currentUser = userService.getCurrentUser();
        messageService.deleteMessage(currentUser, message.getMessageId());
    }
}
