package com.team4.artgallery.aspect;

import com.team4.artgallery.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * {@link RestController} 어노테이션이 달린 클래스의 메소드 혹은 {@link ExceptionHandler} 어노테이션이 달린 메소드의
 * 응답을 {@link ResponseDto} 객체로 감싸 반환하는 Aspect 클래스
 *
 * @apiNote 조건에 해당하는 메소드가 호출될 때 반환 값을 아래와 같이 변환해 반환합니다.
 * <ul>
 *     <li>{@code String} : 반환 값을 {@code message}값으로 {@link ResponseDto} 객체를 생성해 반환</li>
 *     <li>{@code URI} : 반환 값을 {@code url}값으로 {@link ResponseDto} 객체를 생성해 반환</li>
 *     <li>그 외의 값 : 그대로 반환</li>
 * </ul>
 */
@Aspect
@Order(1)
@Component
public class RestResponseWrapperAspect {

    /**
     * {@link RestController} 어노테이션이 달린 클래스의 메소드 혹은 {@link ExceptionHandler} 어노테이션이 달린 메소드가
     * 문자열을 반환하면 {@link ResponseDto} 객체로 감싸 반환합니다.
     *
     * @implNote {@link Around} 어노테이션으로 메서드 실행 전후에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @within} 은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@code value}값의 {@code @annotation}은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@link ProceedingJoinPoint} 객체는 메서드의 정보를 담고 있습니다. 이를 통해 메서드의 실행을 직접 제어할 수 있습니다.
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) || " +
            "@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public Object onAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 실행
        Object result = joinPoint.proceed();

        // 반환값이 ResponseEntity 객체인 경우 body 값을 변환하여 반환
        if (result instanceof ResponseEntity<?> responseEntity) {
            return ResponseEntity
                    .status(responseEntity.getStatusCode())
                    .body(convertResponse(responseEntity.getBody()));
        }

        // 그 외의 경우 반환값을 변환하여 반환
        return convertResponse(result);
    }

    private Object convertResponse(Object value) {
        // 값이 String 객체인 경우 ResponseBody 객체로 감싸 반환
        if (value instanceof String) {
            return new ResponseDto(value.toString(), "");
        }

        // 값이 URI 객체인 경우 ResponseBody 객체로 감싸 반환
        if (value instanceof URI) {
            return new ResponseDto("", value.toString());
        }

        // 그 외의 경우 그대로 반환
        return value;
    }

}