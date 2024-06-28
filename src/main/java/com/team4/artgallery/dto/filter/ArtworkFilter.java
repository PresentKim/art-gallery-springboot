package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
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
    private String displayyn = "Y";

    /**
     * URL 파라미터에 전시 여부를 포함할지 여부
     */
    private boolean includeDisplay = true;

    public boolean urlParamFilter(String fieldName) {
        if (fieldName.equals("displayyn")) {
            return includeDisplay;
        }

        return true;
    }

}