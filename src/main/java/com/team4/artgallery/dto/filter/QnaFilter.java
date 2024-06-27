package com.team4.artgallery.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QnaFilter {

    private String replyyn;
    private String search;

    public boolean reply() {
        return "Y".equals(replyyn);
    }

    public boolean hasReply() {
        return replyyn != null && !replyyn.isEmpty();
    }

    public boolean hasSearch() {
        return search != null && !search.isEmpty();
    }

    public boolean isEmpty() {
        return !hasReply() && !hasSearch();
    }

    public String toUrlParam() {
        if (isEmpty()) {
            return "";
        }

        List<String> params = new ArrayList<>();

        if (hasReply()) {
            params.add("replyyn=" + replyyn);
        }

        if (hasSearch()) {
            params.add("search=" + search);
        }

        return String.join("&", params);
    }

}
