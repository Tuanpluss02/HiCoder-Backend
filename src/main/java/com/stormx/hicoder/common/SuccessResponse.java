package com.stormx.hicoder.common;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public class SuccessResponse extends ResponseGeneral {
    public SuccessResponse(HttpStatus status, String message, String path, Object body) {
        super(LocalDateTime.now().toString(), path, status.value(), message, body, null);
    }

    public SuccessResponse(HttpStatus status, String message, String path, Object pageable, Object body) {
        super(LocalDateTime.now().toString(), path, status.value(), message, pageable, body);
    }
}