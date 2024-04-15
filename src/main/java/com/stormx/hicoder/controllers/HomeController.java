package com.stormx.hicoder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping("/ui/chat")
    public String index() {
        return "index";
    }

    @GetMapping("/ui/reset-password")
    public String resetPassword(@RequestParam("token") String token) {
        return "reset-password";
    }
}