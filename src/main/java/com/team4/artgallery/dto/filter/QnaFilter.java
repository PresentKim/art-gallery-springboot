package com.team4.artgallery.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QnaFilter {

    private String replyyn;
    private String keyword;

    public boolean reply() {
        return "Y".equals(replyyn);
    }

    public boolean hasReply() {
        return replyyn != null && !replyyn.isEmpty();
    }

    public boolean hasSearch() {
        return keyword != null && !keyword.isEmpty();
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
            params.add("keyword=" + keyword);
        }

        return String.join("&", params);
    }

}
