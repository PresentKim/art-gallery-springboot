package com.team4.artgallery.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum NoticeCategory {
    ALL("전체", ""),
    NOTICE("공지사항"),
    EVENT("이벤트"),
    MAGAZINE("매거진"),
    NEWSPAPER("신문");

    private final String korName;
    private final String value;

    NoticeCategory(String korName) {
        this(korName, korName);
    }

    /**
     * 입력된 문자열이 해당 카테고리의 값과 일치하는지 확인합니다.
     *
     * @param str 문자열
     * @return 일치 여부
     */
    public boolean isEquals(String str) {
        return korName.equals(str) || value.equals(str) || this == ALL && str == null;
    }

    /**
     * 관리자가 작성할 수 있는 카테고리 목록을 반환합니다.
     *
     * @return 관리자가 작성할 수 있는 카테고리 목록
     */
    public static List<NoticeCategory> writableValues() {
        return List.of(NOTICE, EVENT);
    }
}