package com.stormx.hicoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HiCoderBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiCoderBackendApplication.class, args);
    }

    @GetMapping("")
    String test() {
        return "Hello, World!";
    }
}
