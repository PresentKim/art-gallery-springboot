package com.team4.artgallery.controller.advice;

import com.team4.artgallery.aspect.CheckAdminAspect;
import com.team4.artgallery.aspect.CheckLoginAspect;
import com.team4.artgallery.aspect.ContentNegotiationExceptionHandlerAspect;
import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 전역 예외 처리 클래스
 * <p>
 * 프로젝트 내에서 발생하는 예외와 스프링 프레임워크에서 발생하는 예외를 처리하는 핸들러 메소드를 가지고 있습니다.
 *
 * @implNote {@link ControllerAdvice} 어노테이션을 사용하면 여러 컨트롤러에 걸쳐 있는 공통의 예외 처리, 데이터 바인딩, 모델 작업 등을 한 곳에서 관리할 수 있습니다.
 * 이를 통해 코드의 중복을 줄이고, 애플리케이션 전반에 걸친 예외 처리 및 데이터 전처리 로직을 효율적으로 관리할 수 있습니다.
 * <p>
 * 이 클래스에선 {@link ExceptionHandler} 어노테이션을 사용하여 예외 처리를 위한 메소드를 정의합니다.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // 프로젝트에서 의도적으로 발생시킨 예외를 처리하는 핸들러 메소드

    /**
     * {@link ResponseStatusException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 프로젝트 내에서 의도적으로 발생시킨 예외로, 예외 메시지와 상태 코드를 가지고 있습니다.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Object handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ResponseDto(e.getReason(), ""));
    }

    /**
     * {@link NotLoginException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote {@link CheckLoginAspect}에서 발생시키는 예외로, 로그인이 필요한 페이지에 로그인하지 않은 상태로 접근했을 때 발생합니다.
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object handleNotLoginException(NotLoginException e) {
        return new ResponseDto("로그인이 필요합니다", e.getReturnUrl());
    }

    /**
     * {@link NotAdminException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote {@link CheckAdminAspect}에서 발생시키는 예외로, 관리자가 아닌 사용자가 관리자 기능에 접근하려고 할 때 발생합니다.
     */
    @ExceptionHandler(NotAdminException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleNotAdminException() {
        // 보안을 위해 관리자가 아닌 경우 해당 페이지 혹은 기능이 존재하지 않는 것처럼 처리합니다
        // (브루트 포스 공격을 방지하기 위함)
        return "요청하신 리소스를 찾을 수 없습니다.";
    }


    // 스프링 프레임워크에서 발생한 예외를 처리하는 핸들러 메소드

    /**
     * {@link NoResourceFoundException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 클라이언트가 요청한 리소스가 서버에 존재하지 않을 때 발생합니다.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleNoResourceFoundException() {
        return "요청하신 리소스를 찾을 수 없습니다.";
    }

    /**
     * {@link MaxUploadSizeExceededException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 업로드 파일 크기가 제한을 초과했을 때 발생합니다.
     * <p>
     * 파일 크기 제한은 {@code application.properties} 파일에서 설정할 수 있습니다.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMaxUploadSizeExceededException() {
        return "파일 크기가 너무 큽니다. 10MB 이하의 파일만 업로드 가능합니다.";
    }

    /**
     * {@link MissingServletRequestParameterException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 {@link RequestParam} 어노테이션으로 요구된 파라미터가 요청에 포함되지 않았을 경우에 발생합니다.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return "파라미터 " + e.getParameterName() + "이(가) 누락되었습니다.";
    }

    /**
     * {@link MethodArgumentTypeMismatchException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 요청의 파라미터 타입이 컨트롤러에서 기대하는 메소드의 파라미터 타입과 일치하지 않을 때 발생합니다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return "파라미터 " + e.getPropertyName() + "이(가) 올바르지 않습니다.";
    }

    /**
     * {@link MethodArgumentNotValidException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 객체 필드의 검증(Validation) 과정에서 요구사항을 만족하지 못하는 파라미터가 있을 때 발생합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getBindingResult().getFieldError());
        return fieldError.getDefaultMessage();
    }

    /**
     * {@link HandlerMethodValidationException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 메소드 파라미터의 검증(Validation) 과정에서 요구사항을 만족하지 못하는 파라미터가 있을 때 발생합니다.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        return e.getAllErrors().get(0).getDefaultMessage();
    }

    /**
     * {@link HttpRequestMethodNotSupportedException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 HTTP 요청 메소드가 컨트롤러에서 지원하지 않는 경우에 발생합니다.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Object handleHttpRequestMethodNotSupportedException() {
        return "지원하지 않는 요청 메소드입니다";
    }

    /**
     * {@link HttpMediaTypeException} 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 스프링에서 HTTP 요청의 ACCEPT 헤더 값을 컨트롤러에서 지원하지 않는 경우에 발생합니다.
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Object handleHttpMediaTypeException() {
        return "지원하지 않는 미디어 타입입니다";
    }

    /**
     * 처리되지 않은 예외를 처리하는 핸들러 메소드
     *
     * @return {@link ContentNegotiationExceptionHandlerAspect} 클래스에서 응답을 자동으로 적절한 객체로 변환
     * @implNote 처리되지 않은 모든 예외는 이 핸들러 메소드에서 처리됩니다.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleAllUncaughtException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        System.out.println(stringWriter);
        return e.getMessage();
    }

}