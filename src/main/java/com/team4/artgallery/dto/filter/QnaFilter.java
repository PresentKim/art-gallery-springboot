package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
