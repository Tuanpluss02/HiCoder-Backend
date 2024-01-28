package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.interfaces.ResponseGeneral;
import com.stormx.hicoder.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", currentUser));
    }
}
