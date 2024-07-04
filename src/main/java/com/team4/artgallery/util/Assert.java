package com.team4.artgallery.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class Assert {

    /**
     * 값이 false가 아닌 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 false가 아닌 경우 예외 발생
     */
    public <T extends Throwable> void isFalse(boolean value, String message, Function<String, T> throwableConstructor) throws T {
        if (value) {
            throw throwableConstructor.apply(message);
        }
    }

    /**
     * 값이 true가 아닌 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 true가 아닌 경우 예외 발생
     */
    public <T extends Throwable> void isTrue(boolean value, String message, Function<String, T> throwableConstructor) throws T {
        isFalse(!value, message, throwableConstructor);
    }

    /**
     * 값이 0인 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 0인 경우 예외 발생
     */
    public <T extends Throwable> void notZero(int value, String message, Function<String, T> throwableConstructor) throws T {
        isFalse(value == 0, message, throwableConstructor);
    }

    /**
     * 값이 null이 아닌 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 null이 아닌 경우 예외 발생
     */
    public <T extends Throwable> void isNull(Object value, String message, Function<String, T> throwableConstructor) throws T {
        isTrue(value == null, message, throwableConstructor);
    }

    /**
     * 값이 null인 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 null인 경우 예외 발생
     */
    public <T extends Throwable> void notNull(Object value, String message, Function<String, T> throwableConstructor) throws T {
        isFalse(value == null, message, throwableConstructor);
    }

    /**
     * 값이 비어있는 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 비어있는 경우 예외 발생
     */
    public <T extends Throwable> void notEmpty(Object value, String message, Function<String, T> throwableConstructor) throws T {
        isTrue(getSize(value) == 0, message, throwableConstructor);
    }

    /**
     * 값의 길이가 1이 아닌 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값의 길이가 1이 아닌 경우 예외 발생
     */
    public <T extends Throwable> void isSingle(Object value, String message, Function<String, T> throwableConstructor) throws T {
        isTrue(getSize(value) == 1, message, throwableConstructor);
    }

    /**
     * 객체로부터 길이를 가져옵니다.
     *
     * @param value 객체
     *              - String: 문자열 길이
     *              - Collection: 컬렉션 크기
     *              - Map: 맵 크기
     *              - Object[]: 배열 길이
     *              - MultipartFile: 파일이 비어있는지 여부
     *              - 그 외: 0
     * @return 길이
     */
    private int getSize(Object value) {
        if (value instanceof String v) {
            return v.strip().length();
        } else if (value instanceof Collection<?> v) {
            return v.size();
        } else if (value instanceof Map<?, ?> v) {
            return v.size();
        } else if (value instanceof Object[] v) {
            return v.length;
        } else if (value instanceof MultipartFile v) {
            return v.isEmpty() ? 0 : 1;
        }

        return 0;
    }

}