package com.team4.artgallery.resolver;

import com.team4.artgallery.annotation.LoginMember;
import com.team4.artgallery.exception.NotLoginException;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final SessionService sessionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        // 세션이 없거나 로그인하지 않은 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            throw new NotLoginException();
        }

        // 로그인한 회원 정보를 반환합니다.
        return memberService.getLoginMember(session);
    }

}