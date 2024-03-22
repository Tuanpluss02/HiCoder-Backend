package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.AuthenticationRequest;
import com.stormx.hicoder.controllers.requests.AuthenticationResponse;
import com.stormx.hicoder.controllers.requests.ResetPasswordRequest;
import com.stormx.hicoder.services.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Include method for login, sign up,...")
@SecurityRequirements()
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Login successfully", request.getRequestURI(), authenticationResponse));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> userRegister(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(authenticationRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Register successfully", request.getRequestURI(), authenticationResponse));
    }

    @PostMapping("/send-mail-rsp")
    public ResponseEntity<?> sendEmailResetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        authenticationService.sendEmailResetPassword(resetPasswordRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Send email reset password successfully", request.getRequestURI(), null));
    }

    @PostMapping("/reset-password")
    public String verifyAndChangePwd(@RequestParam("token") String token) {
        return token;
    }


    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get new access token successfully", request.getRequestURI(), authenticationService.getNewAccessToken(request, response)));
    }

}