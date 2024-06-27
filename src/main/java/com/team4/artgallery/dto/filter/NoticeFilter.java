package com.team4.artgallery.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NoticeFilter {

    private String category;
    private String search;

    public boolean hasCategory() {
        return category != null && !category.isEmpty() && !"전체".equals(category);
    }

    public boolean hasSearch() {
        return search != null && !search.isEmpty();
    }

    public boolean isEmpty() {
        return !hasCategory() && !hasSearch();
    }

    public String toUrlParam() {
        if (isEmpty()) {
            return "";
        }

        List<String> params = new ArrayList<>();
        if (hasCategory()) {
            params.add("category=" + category);
        }

        if (hasSearch()) {
            params.add("search=" + search);
        }

        return String.join("&", params);
    }

}
