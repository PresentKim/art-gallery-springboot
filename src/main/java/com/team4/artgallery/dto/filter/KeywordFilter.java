package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

}
