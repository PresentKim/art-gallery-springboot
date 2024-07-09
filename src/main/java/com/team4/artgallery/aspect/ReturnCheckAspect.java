package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.NotEmptyReturn;
import com.team4.artgallery.aspect.annotation.NotNullReturn;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.util.Assert;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * 메소드의 반환 값을 확인하는 Aspect 클래스
 *
 * @apiNote {@link NotNullReturn} 어노테이션이 붙은 메서드의 반환 값이 null 인 경우 {@link NotFoundException} 예외를 발생시킵니다.
 */
@Aspect
@Component
public class ReturnCheckAspect {

    /**
     * {@code @annotation}을 통해 메서드에 붙은 {@link NotNullReturn} 어노테이션을 받아
     * 반환 값이 null이면 {@link NotNullReturn#exception}에 설정된 예외를 발생시킵니다.
     *
     * @param annotation {@link NotNullReturn} 어노테이션 객체
     * @param result     AOP가 적용된 메서드의 실행 결과
     * @implNote {@link AfterReturning} 어노테이션으로 메서드 실행 후에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @annotation}은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@code returning}값은 현재 처리중인 메서드의 반환 값을 해당 이름의 매개변수로 받을 수 있도록 합니다.
     */
    @AfterReturning(value = "@annotation(annotation)", returning = "result")
    public void onAfterReturningOfNotNullReturn(
            NotNullReturn annotation,
            Object result
    ) throws ResponseStatusException, ReflectiveOperationException {
        if (result == null) {
            throw annotation.exception().getConstructor(String.class).newInstance(annotation.value());
        }
    }

    /**
     * {@code @annotation}을 통해 메서드에 붙은 {@link NotEmptyReturn} 어노테이션을 받아
     * 반환 값이 null 혹은 빈 문자열, 컬렉션, 맵, 배열이면 {@link NotEmptyReturn#exception}에 설정된 예외를 발생시킵니다.
     *
     * @param annotation {@link NotEmptyReturn} 어노테이션 객체
     * @param result     AOP가 적용된 메서드의 실행 결과
     * @implNote {@link AfterReturning} 어노테이션으로 메서드 실행 후에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @annotation}은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@code returning}값은 현재 처리중인 메서드의 반환 값을 해당 이름의 매개변수로 받을 수 있도록 합니다.
     */
    @AfterReturning(value = "@annotation(annotation)", returning = "result")
    public void onAfterReturningOfNotEmptyReturn(
            NotEmptyReturn annotation,
            Object result
    ) throws ResponseStatusException, ReflectiveOperationException {
        if (Assert.checkIsEmpty(result)) {
            throw annotation.exception().getConstructor(String.class).newInstance(annotation.value());
        }
    }

}