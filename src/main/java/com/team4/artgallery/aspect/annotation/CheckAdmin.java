package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.CheckAdminAspect;
import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.controller.advice.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자 권한을 확인할 때 사용하는 어노테이션
 * 관리자 권한이 없거나 로그인하지 않은 경우 {@link NotAdminException} 예외가 발생합니다.
 *
 * @apiNote 아래와 같이 관리자만 접근 가능한 기능에 사용해 해당 요청을 관리자만 사용할 수 있도록 합니다.
 * <blockquote><pre>
 * {@code @RequestMapping("/admin")}
 * {@code @CheckAdmin}
 * public String index(){
 *     return "admin/main";
 * }</pre></blockquote>
 * <p>
 * 클래스 또는 메서드에 사용할 수 있습니다.
 * @implNote 관점 지향 프로그래밍(AOP)을 위해 반복 사용되는 관리자 권한 확인을 어노테이션으로 처리합니다.
 * <p>
 * 관리자 권한 확인은 {@link CheckAdminAspect}에서 처리하며, 관리자 권한이 없는 경우 {@link NotAdminException} 예외가 발생합니다.
 * <p>
 * 최종적으로 예외 핸들링 및 응답 처리는 {@link GlobalExceptionHandler#handleNotAdminException(HttpServletRequest)}에서 처리됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CheckAdmin {
}