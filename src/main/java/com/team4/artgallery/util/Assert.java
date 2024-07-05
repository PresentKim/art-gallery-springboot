package com.team4.artgallery.util;

import com.team4.artgallery.controller.exception.ForbiddenException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.function.Function;

@UtilityClass
public class Assert {

    /**
     * 값이 true가 아닌 경우 {@link ForbiddenException} 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws ForbiddenException 값이 true가 아닌 경우 예외 발생
     * @see #isTrue(boolean, String, Function)
     */
    public void trueOrForbidden(boolean value, String message) throws ForbiddenException {
        isTrue(value, message, ForbiddenException::new);
    }

    /**
     * 값이 true가 아닌 경우 {@link UnauthorizedException} 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws UnauthorizedException 값이 true가 아닌 경우 예외 발생
     * @see #isTrue(boolean, String, Function)
     */
    public void trueOrUnauthorized(boolean value, String message) throws UnauthorizedException {
        isTrue(value, message, UnauthorizedException::new);
    }

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
     * 값이 비어있는 경우 주어진 예외를 발생시킵니다.
     *
     * @param message 예외 메시지
     * @throws T 값이 비어있는 경우 예외 발생
     */
    public <T extends Throwable> void notEmpty(Object value, String message, Function<String, T> throwableConstructor) throws T {
        isFalse(checkIsEmpty(value), message, throwableConstructor);
    }

    /**
     * 값이 비어있는지 확인합니다.
     */
    public boolean checkIsEmpty(Object value) {
        if (value == null) return true;

        if (value instanceof String v) {
            return v.isBlank();
        }

        if (value instanceof Number v) {
            return v.doubleValue() == 0;
        }

        if (value instanceof Iterable<?> v) {
            return !v.iterator().hasNext();
        }

        if (value.getClass().isArray()) {
            return Array.getLength(value) == 0;
        }

        return true;
    }

}