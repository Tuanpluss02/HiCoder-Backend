package com.stormx.hicoder.common;


import com.stormx.hicoder.interfaces.ResponseGeneral;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

public class SuccessResponse extends ResponseGeneral {
    public SuccessResponse(HttpStatus statusCode, String message, Object body) {
        super(statusCode, message, body);
    }
}
