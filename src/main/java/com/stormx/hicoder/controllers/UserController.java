package com.stormx.hicoder.controllers;

import com.stormx.hicoder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/me")
//    ResponseEntity<ResponseGeneral> getCurrentUserDetail(HttpServletRequest request) {
//        User currentUser = userService.getCurrentUser();
//        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get user detail successfully", request.getRequestURI(), currentUser));
//    }


}
