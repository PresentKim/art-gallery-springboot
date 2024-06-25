package com.team4.artgallery.security;

import com.team4.artgallery.annotation.CheckLogin;
import com.team4.artgallery.security.exception.NotAdminException;
import com.team4.artgallery.security.exception.NotLoginException;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SessionAspect {

    private final MemberService memberService;
    private final SessionService sessionService;

    @Before("@annotation(com.team4.artgallery.annotation.CheckLogin)")
    public void checkLogin(JoinPoint joinPoint) {
        // 세션이 없거나 로그인하지 않은 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);

            String returnUrl = checkLogin.returnUrl();
            throw new NotLoginException(memberService.getRedirectToLogin(returnUrl));
        }
    }

    @Before("@annotation(com.team4.artgallery.annotation.CheckAdmin)")
    public void checkAdmin(JoinPoint __) {
        // 세션이 없거나 관리자가 아닌 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isAdmin(session)) {
            throw new NotAdminException();
        }
    }

}