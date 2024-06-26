package com.team4.artgallery.dto.enums;

import java.util.List;

public enum NoticeCategory {
    전체, 공지사항, 이벤트, 매거진, 신문;

    public static List<NoticeCategory> validValues() {
        return List.of(공지사항, 이벤트, 매거진, 신문);
    }

    public static List<NoticeCategory> writableValues() {
        return List.of(공지사항, 이벤트);
    }
}