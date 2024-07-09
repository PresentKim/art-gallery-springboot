package com.team4.artgallery.aspect.exception;

/**
 * 로그인하지 않은 사용자가 로그인이 필요한 기능에 접근할 때 발생하는 예외 클래스
 */
public final class NotLoginException extends RuntimeException {

    private final String returnUrl;

    public NotLoginException(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getReturnUrl() {
        return this.returnUrl;
    }

}
