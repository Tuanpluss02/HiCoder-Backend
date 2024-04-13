package com.stormx.hicoder.controllers;


import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Include method for admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @PostMapping("/permission")
    public ResponseEntity<?> changePermission(@RequestParam String userId, @RequestParam String role, HttpServletRequest request) {
        role = role.toUpperCase();
        User user = userService.getUserById(userId);
       if (role.equals("ADMIN") || role.equals("USER")) {
           Role roleEnum = Role.valueOf(role);
           userService.changeRole(user, roleEnum);
           String[] data = {
                   "User:" + UserDTO.fromUser(user),
                   "New Role:" + role
           };
           return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Change role successfully", request.getRequestURI(), data));
       } else {
           return ResponseEntity.badRequest().body(new SuccessResponse(HttpStatus.BAD_REQUEST, "Role is not valid", request.getRequestURI(), null));
       }
    }
    @DeleteMapping("/user")
    public ResponseEntity<?> removeUser(@RequestParam String userId, HttpServletRequest request) {
        User user = userService.getUserById(userId);
        userService.removeUser(user);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Remove user successfully", request.getRequestURI(), UserDTO.fromUser(user)));
    }
}
