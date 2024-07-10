package com.team4.artgallery.dto.request;

import com.team4.artgallery.dto.filter.IFilter;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;

import java.util.Optional;

public record FilteredGetListRequest<T extends IFilter>(@Valid T filter, @Valid Pagination pagination) {
    public Optional<T> getFilter() {
        return Optional.ofNullable(filter);
    }

    public Optional<Pagination> getPagination() {
        return Optional.ofNullable(pagination);
    }
}
