package com.team4.artgallery.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.team4.artgallery.dto.view.Views;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        @JsonView({Views.Summary.class, Views.Detail.class})
        List<T> content,

        @JsonView({Views.Summary.class, Views.Detail.class})
        int pageNumber,

        @JsonView({Views.Summary.class, Views.Detail.class})
        int pageSize,

        @JsonView({Views.Summary.class, Views.Detail.class})
        long totalElements,

        @JsonView({Views.Summary.class, Views.Detail.class})
        int totalPages
) {

    public PageResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}