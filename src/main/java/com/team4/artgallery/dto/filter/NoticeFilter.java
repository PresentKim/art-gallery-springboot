package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;

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

    public String getKeyword() {
        return this.keyword;
    }

    public NoticeFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public NoticeFilter setCategory(String category) {
        this.category = category;
        return this;
    }

}
