package com.stormx.hicoder.common;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse extends ResponseGeneral {
    public ErrorResponse(HttpStatus status, String message, String path) {
        super(LocalDateTime.now().toString(), path, status.value(), message, null);
    }
}