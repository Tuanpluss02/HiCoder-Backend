package com.stormx.hicoder.common;

import com.stormx.hicoder.exceptions.BadRequestException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    public static boolean isValidSortField(String sortField, Class<?> entityClass) {
        try {
            entityClass.getDeclaredField(sortField);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public static PageRequest calculatePageable(int page, int size, String[] sort, Class<?> clazz) {
        String sortField = sort[0];
        String sortDirection = sort[1];
        if (!isValidSortField(sortField, clazz)) {
            throw new BadRequestException("Invalid sort field");
        }
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }

    public static <T, U> Pair<PaginationInfo, List<U>> extractToDTO(Page<T> entities, Function<T, U> converter) {
        PaginationInfo paginationInfo = new PaginationInfo(entities.getNumber(),
                entities.getSize(), entities.getTotalPages(), entities.getTotalElements());
        List<U> dtoList = entities.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());
        return Pair.of(paginationInfo, dtoList);
    }
}
