package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import com.team4.artgallery.entity.NoticeEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

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

    public Specification<NoticeEntity> toSpec() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                predicates.add(cb.equal(root.get("category"), category));
            }

            if (keyword != null) {
                String pattern = "%" + keyword + "%";
                predicates.add(cb.or(
                        cb.like(root.get("title"), pattern),
                        cb.like(root.get("content"), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
