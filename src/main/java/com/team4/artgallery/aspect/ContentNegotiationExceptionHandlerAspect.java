package com.team4.artgallery.aspect;

import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.helper.RequestProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 콘텐츠 협상 처리를 위해 예외 핸들러의 응답을 요청 헤더의 ACCEPT 값에 따라 적절한 객체로 변환하여 반환하는 Aspect 클래스
 *
 * @implNote 요청 메소드는 단순히 기능의 분류를 의미하고, 응답의 종류를 결정할 때는 요청 헤더의 ACCEPT 값을 참조해야합니다.
 * <p>
 * 따라서 기존 구현된 {@code GET} 요청엔 HTML, {@code POST} 요청엔 JSON 문자열을 반환하는 방식은 잘못된 방식입니다.
 * <p>
 * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Methods">HTTP 요청 메서드</a>
 * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Content_negotiation">콘텐츠 협상</a>
 * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Accept">Accept 요청 HTTP 헤더</a>
 */
@Aspect
@Order(-1)
@Component
public class ContentNegotiationExceptionHandlerAspect {

    private final RequestProvider requestProvider;

    public ContentNegotiationExceptionHandlerAspect(RequestProvider requestProvider) {
        this.requestProvider = requestProvider;
    }

    /**
     * {@link ExceptionHandler} 어노테이션이 붙은 메소드가 호출될 때 요청 헤더의 ACCEPT 값에 따라 적절한 객체로 변환하여 반환합니다.
     *
     * @apiNote 요청 헤더의 ACCEPT 값에 따른 반환 형식은 다음과 같습니다.
     * <ul>
     *     <li>{@code application/json} : 주어진 값 그대로 반환</li>
     *     <li>{@code text/html} : {@link ModelAndView} 객체로 감싸서 반환</li>
     * </ul>
     * @implNote {@link Around} 어노테이션으로 메서드 실행 전후에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @annotation} 은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * <p>
     * {@link ProceedingJoinPoint} 객체는 메서드의 정보를 담고 있습니다. 이를 통해 메서드의 실행을 직접 제어할 수 있습니다.
     */
    @Around("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public Object onAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 실행 후 반환값을 가져옴
        Object result = joinPoint.proceed();

        // 응답 상태 코드를 가져오기 위해 ResponseStatus 어노테이션 확인
        ResponseStatus statusAnnotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ResponseStatus.class);
        HttpStatusCode statusCode = statusAnnotation != null ? statusAnnotation.value() : null;

        // 응답이 ResponseEntity 객체인 경우 응답 상태 코드 갱신
        if (result instanceof ResponseEntity<?> responseEntity) {
            statusCode = responseEntity.getStatusCode();
            result = responseEntity.getBody();
        }

        // application/json 요청인 경우 그대로 반환
        String acceptHeader = requestProvider.getRequest().getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return statusCode == null ? result : ResponseEntity.status(statusCode).body(result);
        }

        // application/json 요청이 아닌 경우 ModelAndView 로 변환
        ModelAndView modelAndView = new ModelAndView();
        if (result instanceof ResponseDto responseDto) {
            String url = responseDto.url();
            modelAndView.addObject("message", responseDto.message());
            modelAndView.addObject("url", url);

            // url 값이 존재하는 경우 alert 페이지로 포워딩
            if (url != null && !url.isEmpty()) {
                modelAndView.setViewName("util/alert");
                return modelAndView;
            }
        }

        // 응답 상태 코드가 NOT FOUND 인 경우 404 페이지로 포워딩
        if (statusCode != null && statusCode.value() == HttpStatus.NOT_FOUND.value()) {
            modelAndView.setViewName("util/404");
        } else { // 그 외의 경우 500 페이지로 포워딩
            modelAndView.setViewName("util/500");
        }

        return modelAndView;
    }

}