package com.team4.artgallery.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ArtworkCategory {
    ALL("전체", ""),
    PAINTING("회화"),
    DRAWING("드로잉"),
    PRINTMAKING("판화"),
    SCULPTURE("조각ㆍ설치"),
    PHOTOGRAPHY("사진"),
    CRAFT("공예"),
    DESIGN("디자인"),
    CALLIGRAPHY("서예");

    private final String korName;
    private final String value;

    ArtworkCategory(String korName) {
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
     * ALL 카테고리를 제외한 유효한 카테고리 목록을 반환합니다.
     *
     * @return 유효한 카테고리 목록
     */
    public static List<ArtworkCategory> validValues() {
        return List.of(PAINTING, DRAWING, PRINTMAKING, SCULPTURE, PHOTOGRAPHY, CRAFT, DESIGN, CALLIGRAPHY);
    }

}