package com.team4.artgallery.aspect.annotation;

import com.team4.artgallery.aspect.QueryAppliedAspect;
import com.team4.artgallery.controller.advice.GlobalExceptionHandler;
import com.team4.artgallery.controller.exception.SqlException;
import org.springframework.web.server.ResponseStatusException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 쿼리 결과 값이 0인 경우 {@link ResponseStatusException} 예외를 발생시키는 어노테이션
 *
 * @apiNote 아래와 같이 INSERT 혹은 UPDATE 쿼리에 변경된 행의 수가 0인 경우 예외 처리를 할 수 있습니다.
 * <blockquote><pre>
 * {@code @QueryApplied}("예술품 정보를 추가하는 중 오류가 발생했습니다.")
 * int createArtwork(ArtworkDto artworkDto);
 * </pre></blockquote>
 * <p>
 * 어노테이션의 {@link #value}를 설정해 메시지를 지정할 수 있고, 설정하지 않으면 기본 메시지를 사용합니다.
 * <p>
 * 메서드에 사용할 수 있습니다.
 * <p>
 * @implNote 관점 지향 프로그래밍(AOP)을 위해 반복 사용되는 쿼리 결과 확인을 어노테이션으로 처리합니다.
 * <p>
 * 쿼리 결과 확인은 {@link QueryAppliedAspect}에서 처리하며, 쿼리 결과 값이 0인 경우 {@link ResponseStatusException} 예외가 발생합니다.
 * {@link QueryApplied#exceptionClass} 값을 기반으로 예외 클래스를 생성하며,
 * {@link ResponseStatusException#reason}에는 {@link #value} 값이 저장됩니다.
 * <p>
 * 최종적으로 예외 핸들링 및 응답 처리는 {@link GlobalExceptionHandler#handleResponseStatusException(ResponseStatusException)}에서 처리됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryApplied {
    /**
     * {@code message} 오류 메시지
     */
    String value() default "오류가 발생했습니다.";

    /**
     * {@code exceptionClass} 예외 클래스
     */
    Class<? extends ResponseStatusException> exceptionClass() default SqlException.class;
}