package com.stormx.hicoder.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public class AppException extends  RuntimeException{
    private HttpStatus  statusCode;
    private String message;
}
