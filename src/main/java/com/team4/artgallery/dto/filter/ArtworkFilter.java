package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtworkFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

    /**
     * 예술품 카테고리
     */
    @FilterField
    private String category;

    /**
     * 예술품 전시 여부
     */
    @FilterField
    private String displayyn;

    /**
     * URL 파라미터에 전시 여부를 포함할지 여부
     */
    private boolean includeDisplay = true;

    /**
     * 주어진 필드 이름을 URL 파라미터로 변환할지 여부를 반환합니다.
     *
     * @param fieldName 필드 이름
     * @return URL 파라미터로 변환할지 여부
     */
    public boolean urlParamFilter(String fieldName) {
        if (fieldName.equals("displayyn")) {
            return includeDisplay;
        }

        return true;
    }

}