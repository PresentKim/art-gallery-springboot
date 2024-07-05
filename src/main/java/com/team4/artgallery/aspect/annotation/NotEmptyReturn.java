package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.ReturnCheckAspect;
import com.team4.artgallery.controller.advice.GlobalExceptionHandler;
import com.team4.artgallery.controller.exception.InternalServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메소드가 null 혹은 빈 문자열, 컬렉션, 맵, 배열 값을 반환할 경우 예외를 발생시키는 어노테이션
 *
 * @apiNote 아래와 같이 사용할 수 있습니다
 * <blockquote><pre>
 * {@code @NotEmptyReturn}("데이터가 하나도 존재하지 않습니다")
 * ExampleDto[] getExamples();
 * </pre></blockquote>
 * <p>
 * 어노테이션의 {@link #value}를 설정해 메시지를 지정할 수 있고, 설정하지 않으면 기본 메시지를 사용합니다.
 * <p>
 * 필요한 경우 {@link #exception}을 설정해 예외 클래스를 지정할 수 있습니다.
 * <p>
 * @implNote 반복되는 null 검증을 어노테이션으로 처리합니다.
 * <p>
 * 반환값 검증은 {@link ReturnCheckAspect}에서 처리하며, 반환 값이 null인 경우 {@link ResponseStatusException} 예외가 발생합니다.
 * {@link ResponseStatusException#reason}에는 {@link #value} 값이 저장됩니다.
 * <p>
 * 최종적으로 예외 핸들링 및 응답 처리는 {@link GlobalExceptionHandler#handleResponseStatusException(ResponseStatusException)}에서 처리됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotEmptyReturn {

    String value() default "데이터를 찾을 수 없습니다";

    Class<? extends ResponseStatusException> exception() default InternalServerErrorException.class;

}