package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.requests.AuthenticationRequest;
import com.stormx.hicoder.controllers.requests.ResetPasswordRequest;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Login successfully", request.getRequestURI(), authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> userRegister(@RequestBody @Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Register successfully", request.getRequestURI(), authenticationService.register(authenticationRequest)));
    }

    @PostMapping("/send-mail-rspwd")
    public ResponseEntity<?> sendEmailResetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        authenticationService.sendEmailResetPassword(resetPasswordRequest);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Send email reset password successfully", request.getRequestURI(), null));
    }

    @PostMapping("/reset-password")
    public void verifyAndChangePwd(@RequestParam("token") String token) {

    }


    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    )  {
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Get new access token successfully", request.getRequestURI(), authenticationService.getNewAccessToken(request, response)));
    }

    private static void checkValidRequest(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.get(0));
        }
    }

}