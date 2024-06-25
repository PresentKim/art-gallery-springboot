package com.team4.artgallery.annotation;

import com.team4.artgallery.controller.GlobalExceptionHandler;
import com.team4.artgallery.security.CheckLoginAspect;
import com.team4.artgallery.security.exception.NotLoginException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인 여부를 확인할 때 사용하는 어노테이션으로 클래스나 메서드에 사용합니다.
 * 로그인하지 않은 경우 {@link NotLoginException} 예외가 발생합니다.
 * <p>
 * 로그인 여부 확인 및 예외 발생은 {@link CheckLoginAspect} 에서,
 * 예외 처리는 {@link GlobalExceptionHandler#handleException(NotLoginException, HttpServletRequest)} 에서 처리합니다.
 * <p>
 * {@link #returnUrl} 또는 {@link #value}로 리다이렉트할 URL 을 지정할 수 있고, 지정하지 않으면 페이지 이동을 하지 않습니다.
 * URL 값에는 <code>${매개변수명}</code> 표현식을 사용할 수 있습니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckLogin {
    @AliasFor("value")
    String returnUrl() default "";

    @AliasFor("returnUrl")
    String value() default "";
}