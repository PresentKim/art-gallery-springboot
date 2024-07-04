package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.CheckLoginAspect;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.controller.advice.GlobalExceptionHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인 여부을 확인할 때 사용하는 어노테이션
 *
 * @apiNote 아래와 같이 로그인한 사용자만 접근 가능한 기능에 사용해 해당 요청을 로그인한 사용자만 사용할 수 있도록 합니다.
 * <blockquote><pre>
 * {@code @RequestMapping("/gallery/update")}
 * {@code @CheckLogin("/gallery/update/${gseq}")}
 * public String update()
 * </pre></blockquote>
 * <p>
 * 어노테이션의 {@link #returnUrl} 또는 {@link #value}를 설정해 이동시킬 URL을 지정할 수 있고, 설정하지 않으면 페이지 이동을 하지 않습니다.
 * 템플릿 문자열처럼 {@code ${매개변수명}} 표현식을 사용할 수 있습니다. 메서드에 존재하는 매개변수 중 해당 이름의 값을 사용합니다.
 * <p>
 * 클래스 또는 메서드에 사용할 수 있습니다.
 * <p>
 * @implNote 관점 지향 프로그래밍(AOP)을 위해 반복 사용되는 로그인 여부 확인을 어노테이션으로 처리합니다.
 * <p>
 * 로그인 여부 확인은 {@link CheckLoginAspect}에서 처리하며, 로그인하지 않은 경우 {@link NotLoginException} 예외가 발생합니다.
 * {@link NotLoginException#getReturnUrl}에는 {@link CheckLogin}의 {@link #returnUrl} 또는 {@link #value}로 설정한 URL이 저장됩니다.
 * <p>
 * 최종적으로 예외 핸들링 및 응답 처리는 {@link GlobalExceptionHandler#handleNotLoginException(NotLoginException)}에서 처리됩니다.
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckLogin {
    @AliasFor("value")
    String returnUrl() default "";

    @AliasFor("returnUrl")
    String value() default "";
}