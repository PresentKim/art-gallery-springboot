package com.team4.artgallery.dto.filter;

import com.team4.artgallery.dto.filter.annotation.FilterField;
import com.team4.artgallery.entity.MemberEntity;
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

    public Specification<MemberEntity> toSpec(String filedName) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null) {
                predicates.add(cb.like(root.get(filedName), "%" + keyword + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
