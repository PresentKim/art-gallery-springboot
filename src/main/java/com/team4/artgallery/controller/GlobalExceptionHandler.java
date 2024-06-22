package com.team4.artgallery.controller;

import org.springframework.core.MethodParameter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, WebRequest request) {
        // 404 에러 처리
        if (e instanceof NoResourceFoundException) {
            return "util/404";
        }

        if (e instanceof MethodArgumentTypeMismatchException ex) {
            MethodParameter parameter = ex.getParameter();
            Object value = ex.getValue();
            String message = "파라미터 " + ex.getPropertyName() + "은(는) "
                    + "반드시 " + parameter.getParameterType().getName() + " 타입이어야 합니다."
                    + " 주어진 값은 " + (value == null ? "null" : value + "(" + value.getClass().getName() + ")") + "이므로 처리할 수 없습니다.";

            Logger.getGlobal().warning(message);
            model.addAttribute("message", message);
            return "util/alert";
        }

        // 에러 내용과 함께 500 페이지로 이동
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        model.addAttribute("errorMessage", stringWriter);

        System.out.println(stringWriter);
        return "util/500";
    }

}