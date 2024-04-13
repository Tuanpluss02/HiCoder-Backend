package com.stormx.hicoder.controllers;


import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.FollowService;
import com.stormx.hicoder.services.NotificationService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stormx.hicoder.common.Utils.extractToDTO;

@RestController()
@RequestMapping(path = "api/v1/follow")
@CrossOrigin(origins = "*")
@Tag(name = "Follow Controller", description = "Include method to manage user's follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;
    private final NotificationService notificationService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse> followNewUser(@PathVariable String userId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userService.getUserById(userId);
        followService.followOperation(currentUser, targetUser);
        notificationService.newFollowNotification(targetUser, currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"Follow user successfully", request.getRequestURI()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessResponse> unfollowUser(@PathVariable String userId, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        User targetUser = userService.getUserById(userId);
        followService.unfollowOperation(currentUser, targetUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK,"Unfollow user successfully", request.getRequestURI()));
    }


    @GetMapping("/all/following")
    public ResponseEntity<SuccessResponse> getAllFollowing(  @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userFollowings = followService.getAllFollowings(currentUser, pageRequest);
        Pair<PaginationInfo, List<UserDTO>> response = extractToDTO(userFollowings, UserDTO::fromUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get all following successfully",
                request.getRequestURI(), response.getLeft(), response.getRight()));

    }

    @GetMapping("/all/follower")
    public ResponseEntity<SuccessResponse> getAllFollower(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userFollowers = followService.getAllFollowers(currentUser, pageRequest);
        Pair<PaginationInfo, List<UserDTO>> response = extractToDTO(userFollowers, UserDTO::fromUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get all followers successfully",
                request.getRequestURI(), response.getLeft(), response.getRight()));
    }
}
