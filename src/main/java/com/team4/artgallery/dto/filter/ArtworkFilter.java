package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import jakarta.validation.constraints.Null;

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
    @Null(message = "displayyn 값은 설정할 수 없습니다", groups = ExcludeDisplay.class) // 요청 파라미터의 바인딩을 방지
    private String displayyn;

    /**
     * URL 파라미터에 전시 여부를 포함할지 여부
     */
    private boolean includeDisplay = true;

    public boolean isFieldIncludedAsUrlParam(String fieldName) {
        if (fieldName.equals("displayyn")) {
            return includeDisplay;
        }

        return true;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public ArtworkFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public ArtworkFilter setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDisplayyn() {
        return this.displayyn;
    }

    public ArtworkFilter setDisplayyn(String displayyn) {
        this.displayyn = displayyn;
        return this;
    }

    public ArtworkFilter setIncludeDisplay(boolean includeDisplay) {
        this.includeDisplay = includeDisplay;
        return this;
    }


    // 그룹 클래스

    /**
     * {@link #displayyn} 필드를 요청 파라미터에서 설정될 수 없도록 설정
     */
    public interface ExcludeDisplay {
    }

}