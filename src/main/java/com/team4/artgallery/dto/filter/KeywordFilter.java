package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;

public class KeywordFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

    public String getKeyword() {
        return this.keyword;
    }

    public KeywordFilter setKeyword(String keyword) {
        this.keyword = keyword;

        return this;
    }

}
