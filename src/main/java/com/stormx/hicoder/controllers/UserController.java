package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.exceptions.AppException;
import com.stormx.hicoder.interfaces.ResponseGeneral;
import com.stormx.hicoder.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    ResponseEntity<ResponseGeneral> getCurrentUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        UserDTO user = userService.loadUserByUsername(authentication.getName());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", user));
    }
}
