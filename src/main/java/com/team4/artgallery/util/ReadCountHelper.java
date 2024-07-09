package com.team4.artgallery.util;

import jakarta.servlet.http.HttpSession;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 조회수를 관리하는 도우미 클래스입니다.
 */
public final class ReadCountHelper {

    private ReadCountHelper() {
        // 인스턴스화 방지
    }

    /**
     * 조회 기록을 확인하고, 조회 기록이 없는 경우 조회수를 증가시킵니다.
     *
     * @param key                       조회 기록을 확인할 키 값
     * @param session                   조회 기록을 확인할 세션
     * @param hashPrefix                주어진 값을 앞에 붙여 해시 문자열로 변환할 접두사
     * @param increaseReadCountFunction 조회수를 증가시키는 함수
     */
    public static <T> void increaseReadCountIfNew(
            T key,
            HttpSession session,
            String hashPrefix,
            Consumer<T> increaseReadCountFunction
    ) {
        increaseReadCountIfNew(key, session, value -> hashPrefix + value, increaseReadCountFunction);
    }

    /**
     * 조회 기록을 확인하고, 조회 기록이 없는 경우 조회수를 증가시킵니다.
     *
     * @param key                       조회 기록을 확인할 키 값
     * @param session                   조회 기록을 확인할 세션
     * @param hashFunction              주어진 값을 해시 문자열으로 변환하는 함수
     * @param increaseReadCountFunction 조회수를 증가시키는 함수
     */
    public static <T> void increaseReadCountIfNew(
            T key,
            HttpSession session,
            Function<T, String> hashFunction,
            Consumer<T> increaseReadCountFunction
    ) {
        // 조회 기록을 해시 문자열로 변환
        String hashKey = hashFunction.apply(key);

        // 조회 기록이 있는 경우 조회수를 증가시키지 않음
        if (session.getAttribute(hashKey) != null) {
            return;
        }

        // 조회 기록이 없는 경우 조회수를 증가시킴
        increaseReadCountFunction.accept(key);

        // 조회 기록을 세션에 저장
        session.setAttribute(hashKey, true);
    }

}