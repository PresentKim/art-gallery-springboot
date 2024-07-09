package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;

public class QnaFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

    /**
     * 문의글 답변 여부
     */
    @FilterField
    private String replyyn;

    public String getKeyword() {
        return this.keyword;
    }

    public QnaFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getReplyyn() {
        return this.replyyn;
    }

    public QnaFilter setReplyyn(String replyyn) {
        this.replyyn = replyyn;
        return this;
    }

}
