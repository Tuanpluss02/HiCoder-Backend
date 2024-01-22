package com.stormx.hicoder.common;


import com.stormx.hicoder.interfaces.ResponseGeneral;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ErrorResponse extends ResponseGeneral {
    public ErrorResponse(HttpStatus statusCode, String errorMessage, Object body) {
        super(statusCode, errorMessage, body);
    }

}