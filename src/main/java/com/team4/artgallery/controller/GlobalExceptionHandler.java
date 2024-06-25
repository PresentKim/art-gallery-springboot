package com.team4.artgallery.controller;

import com.team4.artgallery.util.ajax.ResponseBody;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GlobalExceptionHandler {

    @Delegate
    private final ResponseHelper responseHelper;

    @ExceptionHandler(Exception.class)
    public Object handleGetException(Exception e, HttpServletRequest request) {
        ResponseEntity<ResponseBody> response = handlePostException(e);

        // GET 요청이 아닌 경우에는 그대로 반환
        if (!"GET".equals(request.getMethod())) {
            return response;
        }

        // GET 요청인 경우에는 에러 페이지로 포워딩
        ModelAndView modelAndView = new ModelAndView();
        ResponseBody responseBody = response.getBody();
        if (responseBody != null) {
            modelAndView.addObject("message", responseBody.getMessage());
            modelAndView.addObject("url", responseBody.getUrl());
        }

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            modelAndView.setViewName("util/404");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            modelAndView.setViewName("util/alert");
        } else {
            modelAndView.setViewName("util/500");
        }

        return modelAndView;
    }

    public ResponseEntity<ResponseBody> handlePostException(Exception e) {
        // NoResourceFoundException 예외 처리
        if (e instanceof NoResourceFoundException) {
            return notFound();
        }

        // MaxUploadSizeExceededException 예외 처리
        if (e instanceof MaxUploadSizeExceededException) {
            return badRequest("파일 크기가 너무 큽니다. 10MB 이하의 파일만 업로드 가능합니다.");
        }

        // MissingServletRequestParameterException 예외 처리
        if (e instanceof MissingServletRequestParameterException ex) {
            return badRequest("파라미터 " + ex.getParameterName() + "이(가) 누락되었습니다");
        }

        // MethodArgumentTypeMismatchException 예외 처리
        if (e instanceof MethodArgumentTypeMismatchException ex) {
            MethodParameter parameter = ex.getParameter();
            Object value = ex.getValue();
            String message = "파라미터 " + ex.getPropertyName() + "은(는) "
                    + "반드시 " + parameter.getParameterType().getName() + " 타입이어야 합니다."
                    + " 주어진 값은 " + (value == null ? "null" : value + "(" + value.getClass().getName() + ")") + "이므로 처리할 수 없습니다.";

            Logger.getGlobal().warning(message);
            return badRequest("잘못된 " + ex.getPropertyName() + "값입니다");
        }

        // 에러 내용과 함께 500 에러 반환
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        System.out.println(stringWriter);
        return internalServerError();
    }

}