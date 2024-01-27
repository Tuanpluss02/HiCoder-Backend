package com.stormx.hicoder.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
public abstract class ResponseGeneral {
    HttpStatus statusCode;
    String message;
    Object body;
    int getStatusCode() {
        return statusCode.value();
    }
}
