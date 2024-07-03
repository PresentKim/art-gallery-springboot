package com.team4.artgallery.controller.advice;

import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.service.helper.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.core.MethodParameter;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 전역 예외 처리 클래스
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @Delegate
    private final ResponseService responseHelper;

    @ExceptionHandler(ResponseStatusException.class)
    public Object handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {
        return processResponse(ResponseEntity.status(e.getStatusCode()).body(new ResponseBody(e.getReason(), "")), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFoundException(HttpServletRequest request) {
        return processResponse(notFound(), request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSizeExceededException(HttpServletRequest request) {
        return processResponse(badRequest("파일 크기가 너무 큽니다. 10MB 이하의 파일만 업로드 가능합니다."), request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        return processResponse(badRequest("파라미터 " + e.getParameterName() + "이(가) 누락되었습니다."), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        MethodParameter parameter = e.getParameter();
        Object value = e.getValue();
        String valueString = value == null ? "null" : value + "(" + value.getClass().getName() + ")";
        Logger.getGlobal().warning(
                "파라미터 " + e.getPropertyName() + "은(는) "
                        + parameter.getParameterType().getName() + " 타입이어야 합니다."
                        + " 주어진 값은 " + valueString + "이므로 처리할 수 없습니다."
        );

        return processResponse(badRequest("파라미터 " + e.getPropertyName() + "이(가) 올바르지 않습니다."), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        FieldError fieldError = Objects.requireNonNull(e.getBindingResult().getFieldError());
        return processResponse(badRequest("파라미터 " + fieldError.getField() + "이(가) 올바르지 않습니다."), request);
    }

    @ExceptionHandler(NotLoginException.class)
    public Object handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        return processResponse(unauthorized("로그인이 필요합니다", e.getReturnUrl()), request);
    }

    @ExceptionHandler(NotAdminException.class)
    public Object handleNotAdminException(HttpServletRequest request) {
        // 보안을 위해 관리자가 아닌 경우 해당 페이지 혹은 기능이 존재하지 않는 것처럼 처리합니다
        // (브루트 포스 공격을 방지하기 위함)
        return processResponse(notFound(), request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupportedException(HttpServletRequest request) {
        return processResponse(methodNotAllowed(), request);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public Object handleHttpMediaTypeException(HttpServletRequest request) {
        return processResponse(unsupportedMediaType("지원하지 않는 미디어 타입입니다"), request);
    }

    @ExceptionHandler(Exception.class)
    public Object handleAllUncaughtException(Exception e, HttpServletRequest request) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        System.out.println(stringWriter);

        return processResponse(internalServerError(e.getMessage()), request);
    }

    /**
     * 콘텐츠 협상 처리를 위해 요청 헤더의 ACCEPT 값을 확인하여 {@code application/json}인 경우
     * {@link ResponseEntity} 객체 그대로, 아니면 {@link ModelAndView} 객체로 변환하여 반환합니다.
     *
     * @return 'application/json' 요청인 경우 ResponseEntity, 아니면 ModelAndView
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