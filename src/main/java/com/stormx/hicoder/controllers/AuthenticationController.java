package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.ResponeObject;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.exceptions.ValidationException;
import com.stormx.hicoder.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            logger.error("Error: {}", bindingResult.getAllErrors());
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.getFirst());
        }
        return ResponseEntity.ok(new ResponeObject(HttpStatus.OK,"Login successfully", authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors.getFirst());
        }
        return ResponseEntity.ok(new ResponeObject(HttpStatus.OK,"Register successfully", authenticationService.register(authenticationRequest)));
    }
}