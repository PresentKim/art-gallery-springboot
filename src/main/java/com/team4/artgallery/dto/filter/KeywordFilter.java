package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class KeywordFilter implements IFilter {

    /**
     * 검색어
     */
    @FilterField
    private String keyword;

    public String getKeyword() {
        return this.keyword;
    }

    public KeywordFilter setKeyword(String keyword) {
        this.keyword = keyword;

        return this;
    }

    public Specification<?> toSpec(String... filedNames) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.isBlank()) {
                Predicate[] keywordPredicates = new Predicate[filedNames.length];
                for (int i = 0; i < filedNames.length; i++) {
                    keywordPredicates[i] = cb.like(root.get(filedNames[i]), "%" + keyword + "%");
                }

                predicates.add(cb.or(keywordPredicates));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
