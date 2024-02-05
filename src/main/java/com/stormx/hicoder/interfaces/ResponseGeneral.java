package com.stormx.hicoder.interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public abstract class ResponseGeneral {
    protected String timestamp;
    protected String path;
    protected int status;
    protected String message;
    protected Object body;
}
