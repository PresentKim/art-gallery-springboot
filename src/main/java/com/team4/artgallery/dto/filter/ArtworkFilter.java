package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import com.team4.artgallery.entity.ArtworkEntity;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.Null;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArtworkFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    @Parameter(name = "keyword", description = "검색어", example = "풍경화", allowEmptyValue = true)
    private String keyword;

    /**
     * 예술품 카테고리
     */
    @FilterField
    @Parameter(name = "category", description = "카테고리", example = "PAINTING", allowEmptyValue = true)
    private String category;

    /**
     * 예술품 전시 여부
     */
    @FilterField
    @Hidden
    @Null(message = "displayyn 값은 설정할 수 없습니다", groups = ExcludeDisplay.class) // 요청 파라미터의 바인딩을 방지
    private Character displayyn;

    /**
     * URL 파라미터에 전시 여부를 포함할지 여부
     */
    @Hidden
    private boolean includeDisplay = true;

    @Hidden
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

    public Character getDisplayyn() {
        return this.displayyn;
    }

    public ArtworkFilter setDisplayyn(Character displayyn) {
        this.displayyn = displayyn;
        return this;
    }

    @Hidden
    public ArtworkFilter setIncludeDisplay(boolean includeDisplay) {
        this.includeDisplay = includeDisplay;
        return this;
    }

    public Specification<ArtworkEntity> toSpec() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (displayyn != null) {
                predicates.add(cb.equal(root.get("display"), displayyn == 'Y'));
            }

            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword + "%";
                predicates.add(cb.or(
                        cb.like(root.get("name"), pattern),
                        cb.like(root.get("artist"), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 그룹 클래스

    /**
     * {@link #displayyn} 필드를 요청 파라미터에서 설정될 수 없도록 설정
     */
    public interface ExcludeDisplay {
    }

}