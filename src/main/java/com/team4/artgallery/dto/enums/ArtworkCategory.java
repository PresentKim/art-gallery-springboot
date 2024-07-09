package com.team4.artgallery.dto.enums;

import lombok.Getter;

import java.util.List;

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

    @Getter
    private final String korName;

    @Getter
    private final String value;

    ArtworkCategory(String korName) {
        this(korName, korName);
    }

    ArtworkCategory(String korName, String value) {
        this.korName = korName;
        this.value = value;
    }

    /**
     * 입력된 문자열이 카테고리의 값 혹은 한글 이름과 일치하는지 확인합니다.
     *
     * @return 일치 여부
     * @apiNote ALL 카테고리는 null 값도 일치한다고 판단합니다.
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