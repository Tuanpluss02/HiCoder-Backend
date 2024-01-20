package com.stormx.hicoder.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler({ ExceptionA.class, ExceptionB.class })
//    public ResponseEntity<String> handleExceptionA(Exception e) {
//        return ResponseEntity.status(432).body(e.getMessage());
//    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        logger.error(e.getLocalizedMessage());
        return ResponseEntity.status(500).body("");
    }
}

