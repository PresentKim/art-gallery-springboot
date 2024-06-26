package com.team4.artgallery.controller;

import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.service.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @Delegate
    private final ResponseService responseHelper;

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        System.out.println(stringWriter);

        return processResponse(internalServerError(), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleException(NoResourceFoundException e, HttpServletRequest request) {
        return processResponse(notFound(), request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        return processResponse(badRequest("파일 크기가 너무 큽니다. 10MB 이하의 파일만 업로드 가능합니다."), request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleException(MissingServletRequestParameterException e, HttpServletRequest request) {
        return processResponse(badRequest("파라미터 " + e.getParameterName() + "이(가) 누락되었습니다."), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
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
    public Object handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        return processResponse(badRequest(e.getBindingResult()), request);
    }

    @ExceptionHandler(NotLoginException.class)
    public Object handleException(NotLoginException e, HttpServletRequest request) {
        return processResponse(unauthorized("로그인이 필요합니다", e.getReturnUrl()), request);
    }

    @ExceptionHandler(NotAdminException.class)
    public Object handleException(NotAdminException e, HttpServletRequest request) {
        // 보안을 위해 관리자가 아닌 경우 해당 페이지 혹은 기능이 존재하지 않는 것처럼 처리합니다
        // (브루트 포스 공격을 방지하기 위함)
        return processResponse(notFound(), request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return processResponse(methodNotAllowed(), request);
    }


    /**
     * 예외로부터 생성된 ResponseEntity 를 GET 요청인 경우 ModelAndView 로 변환하여 반환, POST 요청인 경우 그대로 반환
     *
     * @param response ResponseEntity 객체
     * @param request  HttpServletRequest 객체
     * @return GET 요청인 경우 ModelAndView, POST 요청인 경우 ResponseEntity
     */
    private Object processResponse(ResponseEntity<ResponseBody> response, HttpServletRequest request) {
        // GET 요청이 아닌 경우에는 그대로 반환
        if (!"GET".equals(request.getMethod())) {
            return response;
        }

        // GET 요청인 경우에는 에러 페이지로 포워딩
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