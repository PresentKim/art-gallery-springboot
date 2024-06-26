package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.CheckAdminAspect;
import com.team4.artgallery.controller.GlobalExceptionHandler;
import com.team4.artgallery.aspect.exception.NotAdminException;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자 권한을 확인할 때 사용하는 어노테이션으로 클래스나 메서드에 사용합니다.
 * 관리자 권한이 없거나 로그인하지 않은 경우 {@link NotAdminException} 예외가 발생합니다.
 * <p>
 * 관리자 권한 확인 및 예외 발생은 {@link CheckAdminAspect} 에서,
 * 예외 처리는 {@link GlobalExceptionHandler#handleException(NotAdminException, HttpServletRequest)} 에서 처리합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckAdmin {
}