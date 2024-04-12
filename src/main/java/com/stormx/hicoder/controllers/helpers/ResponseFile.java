package com.stormx.hicoder.controllers.helpers;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;

    public ResponseFile(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

}