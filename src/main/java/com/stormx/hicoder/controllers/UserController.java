package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.ResponseGeneral;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Include methods to modify, get user information")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    ResponseEntity<ResponseGeneral> getCurrentUserDetail(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        UserDTO response = new UserDTO(currentUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", request.getRequestURI(), response));
    }

}
