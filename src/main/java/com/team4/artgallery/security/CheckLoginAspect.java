package com.team4.artgallery.security;

import com.team4.artgallery.annotation.CheckLogin;
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
public class CheckLoginAspect {

    private final MemberService memberService;
    private final SessionService sessionService;

    @Before("@annotation(com.team4.artgallery.annotation.CheckLogin)")
    public void checkLogin(JoinPoint joinPoint) {
        // 세션이 없거나 로그인하지 않은 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            // JoinPoint 객체로부터 메서드 시그니처를 가져와서 메서드에 추가된 CheckLogin 어노테이션 객체를 가져옵니다.
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);

            // 동적인 returnUrl을 생성하기 위해 파라미터 이름과 값을 가져옵니다.
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();

            // CheckLogin 어노테이션 객체의 returnUrl 값을 가져와 ${파라미터 이름} 형식으로 작성된 파라미터 값으로 치환합니다.
            // ex) /gallery/update?gseq=${gseq} -> /gallery/update?gseq=1
            String returnUrl = checkLogin.value();
            for (int i = 0; i < paramNames.length; i++) {
                if (returnUrl.contains("${" + paramNames[i] + "}")) {
                    returnUrl = returnUrl.replace("${" + paramNames[i] + "}", args[i].toString());
                }
            }

            // NotLoginException 예외를 발생시킵니다.
            throw new NotLoginException(memberService.getRedirectToLogin(returnUrl));
        }
    }

}