package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

    /**
     * 소식지 카테고리
     */
    @FilterField
    private String category;

}
