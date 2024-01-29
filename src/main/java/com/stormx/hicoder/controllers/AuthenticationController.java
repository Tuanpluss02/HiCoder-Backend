package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.ResetPasswordDTO;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.interfaces.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/auth",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(path = "/login")
    public ResponseEntity<?> userLogin(@Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Login successfully", request.getRequestURI(), authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> userRegister(@Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.OK, "Register successfully", request.getRequestURI(), authenticationService.register(authenticationRequest)));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> sendEmailResetPassword(@Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult, HttpServletRequest request) throws ValidationException {
        checkValidRequest(bindingResult);
        authenticationService.sendEmailResetPassword(resetPasswordDTO);
        return ResponseEntity.accepted().build();
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
            throw new ValidationException(errors.getFirst());
        }
    }

}