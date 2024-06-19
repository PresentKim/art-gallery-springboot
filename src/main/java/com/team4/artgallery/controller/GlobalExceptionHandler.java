package com.team4.artgallery.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @RequestMapping("/error")
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        //noinspection CallToPrintStackTrace
        e.printStackTrace();

        // 404 에러 처리
        if (e instanceof NoResourceFoundException) {
            return "util/404";
        }

        // 에러 내용과 함께 500 페이지로 이동
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        model.addAttribute("errorMessage", stringWriter);
        return "util/500";
    }

}