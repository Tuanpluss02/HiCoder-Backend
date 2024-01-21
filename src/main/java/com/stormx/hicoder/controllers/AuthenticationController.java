package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.ResponeObject;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.services.AuthenticationService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    public ResponseEntity<?> userLogin(@Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            logger.error("Error: {}", bindingResult.getAllErrors());
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.getFirst());
        }
        return ResponseEntity.ok(new ResponeObject(HttpStatus.OK, "Login successfully", authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> userRegister(@Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.getFirst());
        }
        return ResponseEntity.ok(new ResponeObject(HttpStatus.OK, "Register successfully", authenticationService.register(authenticationRequest)));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}