package com.team4.artgallery.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArtworkFilter {

    private String category;
    private String displayyn;
    private String keyword;

    public boolean hasCategory() {
        return category != null && !category.isEmpty() && !"전체".equals(category);
    }

    public boolean hasDisplay() {
        return displayyn != null && !displayyn.isEmpty();
    }

    public boolean hasSearch() {
        return keyword != null && !keyword.isEmpty();
    }

    public String toUrlParam(boolean includeDisplay) {
        List<String> params = new ArrayList<>();
        if (hasCategory()) {
            params.add("category=" + category);
        }

        if (includeDisplay && hasDisplay()) {
            params.add("displayyn=" + displayyn);
        }

        if (hasSearch()) {
            params.add("keyword=" + keyword);
        }

        return String.join("&", params);
    }

}