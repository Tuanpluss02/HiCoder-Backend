package com.stormx.hicoder.controllers;


import com.stormx.hicoder.common.PaginationInfo;
import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stormx.hicoder.common.Utils.calculatePageable;
import static com.stormx.hicoder.common.Utils.extractToDTO;

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
           return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Change role successfully, new role: " + role, request.getRequestURI(), UserDTO.fromUser(user)));
       } else {
           return ResponseEntity.badRequest().body(new SuccessResponse(HttpStatus.BAD_REQUEST, "Role is not valid", request.getRequestURI(), null));
       }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "username,desc") String[] sort,
                                      HttpServletRequest request) {
        PageRequest pageRequest = calculatePageable(page, size, sort, UserDTO.class);
        Page<User> users = userService.getAllUsers(pageRequest);
        Pair<PaginationInfo, List<UserDTO>> response = extractToDTO(users, UserDTO::fromUser);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get users successfully", request.getRequestURI(), response.getLeft(), response.getRight()));
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> removeUser(@RequestParam String userId, HttpServletRequest request) {
        User user = userService.getUserById(userId);
        userService.removeUser(user);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Remove user successfully", request.getRequestURI(), UserDTO.fromUser(user)));
    }
}
