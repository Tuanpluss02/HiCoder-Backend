package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.helpers.AuthenticationRequest;
import com.stormx.hicoder.controllers.helpers.AuthenticationResponse;
import com.stormx.hicoder.controllers.helpers.NewPasswordRequest;
import com.stormx.hicoder.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Include method for login, sign up,...")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @SecurityRequirements()
    @PostMapping(path = "/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Login successfully", request.getRequestURI(), authenticationResponse));
    }
    @SecurityRequirements()
    @PostMapping(path = "/register")
    public ResponseEntity<?> userRegister(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(authenticationRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Register successfully", request.getRequestURI(), authenticationResponse));
    }

    @SecurityRequirements()
    @PostMapping("/reset-password")
    public ResponseEntity<?> requesdSendEmail(@RequestParam("email") String email, HttpServletRequest request) throws MessagingException {
        authenticationService.sendEmailResetPassword(email);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Reset password email sent successfully", request.getRequestURI(), null));
    }

    @SecurityRequirements()
    @PostMapping("/reset")
    public ResponseEntity<?>  verifyAndChangePwd(@RequestParam("token") String token, @Valid @RequestBody NewPasswordRequest newPassword) {
         authenticationService.verifyAndChangePwd(token, newPassword.getPassword());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Reset password successfully", null, null));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get new access token successfully", request.getRequestURI(), authenticationService.getNewAccessToken(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Logout successfully", request.getRequestURI(), null));
    }

}