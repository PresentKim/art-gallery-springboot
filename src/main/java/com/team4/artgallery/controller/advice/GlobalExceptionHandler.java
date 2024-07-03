package com.team4.artgallery.controller.advice;

import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.service.helper.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 전역 예외 처리 클래스
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @Delegate
    private final ResponseService responseHelper;

    // 프로젝트에서 의도적으로 발생시킨 예외를 처리하는 핸들러 메소드

    /**
     * {@link ResponseStatusException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link ResponseStatusException} 예외는 의도적으로 발생시킨 예외로, 예외 메시지와 상태 코드를 가지고 있습니다.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Object handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        return processResponse(ResponseEntity.status(e.getStatusCode()).body(new ResponseBody(e.getReason(), "")), request);
    }

    /**
     * {@link NotLoginException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link NotLoginException} 예외는 로그인이 필요한 페이지에 로그인하지 않은 상태로 접근했을 때 발생합니다.
     */
    @ExceptionHandler(NotLoginException.class)
    public Object handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        return processResponse(unauthorized("로그인이 필요합니다", e.getReturnUrl()), request);
    }

    /**
     * {@link NotAdminException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link NotAdminException} 예외는 관리자가 아닌 사용자가 관리자 기능에 접근하려고 할 때 발생합니다.
     */
    @ExceptionHandler(NotAdminException.class)
    public Object handleNotAdminException(HttpServletRequest request) {
        // 보안을 위해 관리자가 아닌 경우 해당 페이지 혹은 기능이 존재하지 않는 것처럼 처리합니다
        // (브루트 포스 공격을 방지하기 위함)
        return processResponse(notFound(), request);
    }


    // 스프링 프레임워크에서 발생한 예외를 처리하는 핸들러 메소드

    /**
     * {@link NoResourceFoundException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link NoResourceFoundException} 예외는 스프링에서 클라이언트가 요청한 리소스가 서버에 존재하지 않을 때 발생합니다.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFoundException(HttpServletRequest request) {
        return processResponse(notFound(), request);
    }

    /**
     * {@link MaxUploadSizeExceededException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link MaxUploadSizeExceededException} 예외는 파일 업로드 시 파일 크기가 제한을 초과했을 때 발생합니다.
     * <p>
     * 파일 크기 제한은 {@code application.properties} 파일에서 설정할 수 있습니다.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSizeExceededException(HttpServletRequest request) {
        return processResponse(badRequest("파일 크기가 너무 큽니다. 10MB 이하의 파일만 업로드 가능합니다."), request);
    }

    /**
     * {@link MissingServletRequestParameterException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link MissingServletRequestParameterException} 예외는 스프링에서
     * {@code @RequestParam} 어노테이션을 사용하여 필수로 지정된 요청 파라미터가 요청에 포함되지 않았을 경우에 발생합니다.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        return processResponse(badRequest("파라미터 " + e.getParameterName() + "이(가) 누락되었습니다."), request);
    }

    /**
     * {@link MethodArgumentTypeMismatchException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link MethodArgumentTypeMismatchException} 예외는 스프링에서
     * 요청의 파라미터 타입이 컨트롤러에서 기대하는 메소드의 파라미터 타입과 일치하지 않을 때 발생합니다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        return processResponse(badRequest("파라미터 " + e.getPropertyName() + "이(가) 올바르지 않습니다."), request);
    }

    /**
     * {@link MethodArgumentNotValidException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link MethodArgumentNotValidException} 예외는 스프링에서
     * 객체 필드의 검증(Validation) 과정에서 요구사항을 만족하지 못하는 파라미터가 있을 때 발생합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        FieldError fieldError = Objects.requireNonNull(e.getBindingResult().getFieldError());
        return processResponse(badRequest("파라미터 " + fieldError.getField() + "이(가) 올바르지 않습니다."), request);
    }

    /**
     * {@link HandlerMethodValidationException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link HandlerMethodValidationException} 예외는 스프링에서
     * 메소드 파라미터의 검증(Validation) 과정에서 요구사항을 만족하지 못하는 파라미터가 있을 때 발생합니다.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Object handleHandlerMethodValidationException(HandlerMethodValidationException e, HttpServletRequest request) {
        return processResponse(badRequest(e.getAllErrors().get(0).getDefaultMessage()), request);
    }

    /**
     * {@link HttpRequestMethodNotSupportedException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link HttpRequestMethodNotSupportedException} 예외는 스프링에서
     * HTTP 요청 메소드가 컨트롤러에서 지원하지 않는 경우에 발생합니다.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
        return processResponse(methodNotAllowed("지원하지 않는 요청 메소드입니다"), request);
    }

    /**
     * {@link HttpMediaTypeException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     * @implNote {@link HttpMediaTypeException} 예외는 스프링에서
     * HTTP 요청의 ACCEPT 헤더 값을 컨트롤러에서 지원하지 않는 경우에 발생합니다.
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    public Object handleHttpMediaTypeException(HttpServletRequest request) {
        return processResponse(unsupportedMediaType("지원하지 않는 미디어 타입입니다"), request);
    }

    /**
     * 처리되지 않은 예외를 처리하는 핸들러 메소드
     *
     * @return {@link #processResponse} 메소드를 통해 적절한 응답 객체를 반환
     */
    @ExceptionHandler(Exception.class)
    public Object handleAllUncaughtException(Exception e, HttpServletRequest request) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        System.out.println(stringWriter);

        return processResponse(internalServerError(e.getMessage()), request);
    }

    /**
     * 콘텐츠 협상 처리를 지키기 위해 요청 헤더의 ACCEPT 값을 확인하여 적절한 객체로 변환하여 반환합니다.
     *
     * @return ACCEPT 값이 {@code application/json}인 경우 {@link ResponseEntity} 객체, 아니면 {@link ModelAndView} 객체
     * @implNote 요청 메소드는 단순히 기능의 분류를 의미하고, 응답의 종류를 결정할 때는 요청 헤더의 ACCEPT 값을 참조해야합니다.
     * <p>
     * 따라서 기존 구현된 {@code GET} 요청엔 HTML, {@code POST} 요청엔 JSON을 반환하는 방식은 잘못된 방식입니다.
     * <p>
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Methods">HTTP 요청 메서드</a>
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Content_negotiation">콘텐츠 협상</a>
     * @see <a href="https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Accept">Accept 요청 HTTP 헤더</a>
     */
    private Object processResponse(ResponseEntity<ResponseBody> response, HttpServletRequest request) {
        // application/json 요청인 경우 그대로 반환
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return response;
        }

        // application/json 요청이 아닌 경우 ModelAndView 로 변환
        ModelAndView modelAndView = new ModelAndView();
        ResponseBody responseBody = response.getBody();
        if (responseBody != null) {
            String url = responseBody.getUrl();
            modelAndView.addObject("message", responseBody.getMessage());
            modelAndView.addObject("url", url);

            // url 값이 존재하는 경우 alert 페이지로 포워딩
            if (url != null && !url.isEmpty()) {
                modelAndView.setViewName("util/alert");
                return modelAndView;
            }
        }

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            modelAndView.setViewName("util/404");
        } else {
            modelAndView.setViewName("util/500");
        }

        return modelAndView;
    }

}