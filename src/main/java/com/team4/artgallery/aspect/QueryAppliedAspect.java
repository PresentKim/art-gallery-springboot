package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.QueryApplied;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * 쿼리 결과 값이 0인 경우 {@link ResponseStatusException} 예외를 발생시키는 어노테이션
 *
 * @apiNote {@link QueryApplied} 메서드가 호출될 때 반환 값을 확인합니다.
 * <p>
 * 반환 값이 0인 경우 {@link QueryApplied#exceptionClass} 값을 기반으로 {@link ResponseStatusException}를 상속받은 예외를 발생시킵니다.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class QueryAppliedAspect {

    /**
     * {@code @annotation}을 통해 메서드에 붙은 {@link QueryApplied} 어노테이션을 받아
     * 반환 값이 0이면 {@link QueryApplied#exceptionClass} 값을 기반으로 {@link ResponseStatusException}를 상속받은 예외를 발생시킵니다.
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
    public void onAfterReturning(QueryApplied annotation, Object result) throws ResponseStatusException, ReflectiveOperationException {

        // 반환 값이 Integer 타입이 아닌 경우 예외 발생
        if (!(result instanceof Integer)) {
            throw new RuntimeException("반환 타입이 Integer 타입이 아닌 메서드에 @QueryApplied 어노테이션을 사용할 수 없습니다.");
        }

        // 반환 값이 0인 경우 예외 발생
        if ((int) result == 0) {
            throw annotation.exceptionClass().getConstructor(String.class).newInstance(annotation.value());
        }
    }

}