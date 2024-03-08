package com.stormx.hicoder.common;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class SuccessResponse {
    private String timestamp;
    private String path;
    private HttpStatus status;
    private String message;
    private Object body;

    public SuccessResponse(HttpStatus status, String message, String path, Object body) {
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
        this.status = status;
        this.message = message;
        this.body = body;
    }
}