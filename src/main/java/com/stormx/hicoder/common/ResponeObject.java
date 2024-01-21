package com.stormx.hicoder.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponeObject {
    private int statusCode;
    private String message;
    private Object body;

}
