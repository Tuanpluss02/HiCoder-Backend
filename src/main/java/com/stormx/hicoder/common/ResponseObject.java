package com.stormx.hicoder.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseObject {
    private HttpStatus statusCode;
    private String message;
    private Object body;
}
