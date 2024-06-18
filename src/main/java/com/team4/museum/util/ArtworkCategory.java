package com.team4.museum.util;

import java.util.List;

public enum ArtworkCategory {
    전체, 회화, 드로잉, 판화, 조각ㆍ설치, 사진, 공예, 디자인, 서예;

    public static List<ArtworkCategory> validValues() {
        return List.of(회화, 드로잉, 판화, 조각ㆍ설치, 사진, 공예, 디자인, 서예);
    }
}