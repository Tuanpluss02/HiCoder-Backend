package com.stormx.hicoder.controllers;

import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    List<User> getCurrentUserDetail(){
        return userRepository.findAll();
    }
}
