package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import jakarta.validation.constraints.Null;
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
    @Null(groups = OnlyDisplay.class, message = "전시 여부는 직접 설정할 수 없습니다.")
    private String displayyn = "Y";

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


    // 그룹 클래스

    /**
     * 일반 사용자의 예술품 요청에 사용하는 그룹
     * <p>
     * {@link #displayyn} 값을 "Y"로 고정합니다.
     */
    public interface OnlyDisplay {
    }

}