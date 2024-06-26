package com.team4.artgallery.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PaginationConverter implements Converter<Integer, Pagination> {

    @Override
    public Pagination convert(@Nullable Integer source) {
        // 파라미터 값을 현재 페이지로 하는 Pagination 객체 반환
        return new Pagination().setCurrentPage(source == null ? 1 : source);
    }

}