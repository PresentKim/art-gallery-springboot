package com.team4.artgallery.controller.resolver;

import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.helper.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 로그인한 회원 정보({@link MemberDto}) 객체를 파라미터로 주입하는 메소드 인자 처리 클래스
 *
 * @implNote {@link LoginMember} 어노테이션이 붙은 매개변수에 로그인한 회원 정보를 주입합니다.
 * <p>
 * 세션이 없거나 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
 */
@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;
    private final SessionService sessionService;

    /**
     * 주어진 메소드 매개변수가 이 리졸버에서 지원되는지 여부를 반환
     *
     * @implNote 매개변수에 {@link LoginMember} 어노테이션이 붙어 있는 지 확인합니다.
     * <p>
     * 이 메소드에서 {@code true}를 반환하면 {@link #resolveArgument} 메소드가 호출됩니다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    /**
     * 메소드 매개변수를 지정된 요청의 인수 값으로 해결합니다.
     *
     * @implNote 로그인 정보가 존재하는 경우 로그인한 회원 정보{@link MemberDto}를 반환합니다.
     * <p>
     * 세션이 없거나 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
     */
    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        // 세션이 없거나 로그인하지 않은 경우 NotLoginException 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            throw new NotLoginException();
        }

        // 로그인한 회원 정보를 반환합니다.
        return memberService.getLoginMember(session);
    }

}