package com.stormx.hicoder.common;


import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//public class SuccessResponse<T> extends ResponseGeneral<T> {
//    public SuccessResponse(HttpStatus status, String message, String path, T body) {
//        super(LocalDateTime.now().toString(), path, status.value(), message, body);
//    }
//}
@Data
public class SuccessResponse {
    private String timestamp;
    private String path;
    private HttpStatus status;
    private String message;
    private Object body;

    @SneakyThrows
    public SuccessResponse(HttpStatus status, String message, String path, Object body) {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        Object json = ow.writeValueAsString(body);
        this.timestamp = LocalDateTime.now().toString();
        this.path = path;
        this.status = status;
        this.message = message;
        this.body = body;
    }
}