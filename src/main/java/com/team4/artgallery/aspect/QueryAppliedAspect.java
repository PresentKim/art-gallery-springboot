package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.aspect.annotation.QueryApplied;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.controller.exception.SqlException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 로그인 여부을 확인하는 Aspect 클래스
 *
 * @apiNote {@link CheckLogin} 어노테이션이 붙은 메서드나 클래스의 메소드가 호출될 때 로인 여부를 확인합니다.
 * <p>
 * 세션 정보가 없거나 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class QueryAppliedAspect {

    /**
     * {@code @annotation}을 통해 메서드에 붙은 {@link QueryApplied} 어노테이션을 받아
     * 반환 값이 0이면 {@link SqlException} 예외를 발생시킵니다.
     *
     * @param annotation {@link QueryApplied} 어노테이션 객체
     * @param result     AOP가 적용된 메서드의 실행 결과
     * @implNote {@link AfterReturning} 어노테이션으로 메서드 실행 후에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @annotation}은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@code returning}값은 현재 처리중인 메서드의 반환 값을 해당 이름의 매개변수로 받을 수 있도록 합니다.
     */
    @AfterReturning(value = "@annotation(annotation)", returning = "result")
    public void onAfterReturning(QueryApplied annotation, Object result) {
        // 반환 값이 Integer 타입이 아닌 경우 예외 발생
        if (!(result instanceof Integer)) {
            throw new RuntimeException("반환 타입이 Integer 타입이 아닌 메서드에 @QueryApplied 어노테이션을 사용할 수 없습니다.");
        }

        // 반환 값이 0인 경우 예외 발생
        if ((int) result == 0) {
            throw new SqlException(annotation.value());
        }
    }

}