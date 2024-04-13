package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.helpers.UpdateUserProfile;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.services.TokenService;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Include methods to modify, get user information")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/me")
    ResponseEntity<ResponseGeneral> getCurrentUserDetail(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        UserDTO response = UserDTO.fromUser(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", request.getRequestURI(), response));
    }

    @PostMapping("/notification")
    ResponseEntity<ResponseGeneral> pushRegistrationToken(@RequestBody String token, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        tokenService.saveDeviceToken(currentUser, token);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Save device token successfully", request.getRequestURI()));
    }

    @PutMapping("/update")
    ResponseEntity<ResponseGeneral> updateUserDetail(@Valid @RequestBody UpdateUserProfile updateUserProfile, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        userService.updateUser(currentUser, updateUserProfile);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update user detail successfully", request.getRequestURI()));
    }

    @PutMapping("/update/avatar")
    ResponseEntity<ResponseGeneral> updateAvatar(@RequestParam String avatarUrl, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        userService.updateAvatar(currentUser, avatarUrl);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Update avatar successfully", request.getRequestURI()));
    }
}
