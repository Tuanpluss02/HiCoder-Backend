package com.stormx.hicoder.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationInfo {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
