package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "Y", message = "잘못된 파라미터가 전달되었습니다", groups = ExcludeDisplay.class) // 요청의 파라미터에서 설정될 수 없도록 설정
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


    // 그룹 클래스

    /**
     * {@link #displayyn} 필드를 요청의 파라미터에서 설정될 수 없도록 설정
     */
    public interface ExcludeDisplay {
    }

}