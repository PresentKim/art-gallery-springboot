package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckLoginAspect {

    private final MemberService memberService;
    private final SessionService sessionService;

    /**
     * <code>@annotation</code>을 통해 메서드에 붙은 {@link CheckLogin} 어노테이션을 받아
     * {@link #checkLogin(JoinPoint, CheckLogin)} 메서드에 전달합니다.
     *
     * @implNote @Before(value = "@annotation(com.team4.artgallery.aspect.annotation.CheckAdmin)")
     * 위와 같은 방식으로 직접 클래스를 작성해도 되지만, "어노테이션 매개변수" 방식을 통해 사용할 수 있습니다.
     * 이 경우 해당 어노테이션 매개변수의 이름을 클래스 대신 사용하면 됩니다.
     * {@see https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/advice.html}
     */
    @Before(value = "@annotation(annotation)")
    public void onAnnotation(JoinPoint joinPoint, CheckLogin annotation) {
        checkLogin(joinPoint, annotation);
    }

    /**
     * <code>@within</code>을 통해 클래스에 붙은 {@link CheckLogin} 어노테이션을 받아
     * {@link #checkLogin(JoinPoint, CheckLogin)} 메서드에 전달합니다.
     *
     * @implNote @within() 은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     */
    @Before(value = "@within(annotation)")
    public void onWithin(JoinPoint joinPoint, CheckLogin annotation) {
        checkLogin(joinPoint, annotation);
    }

    /**
     * CheckLogin 어노테이션을 처리하는 메서드입니다.
     * <p>
     * 세션이 없거나 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
     *
     * @param joinPoint  조인 포인트
     * @param annotation CheckLogin 어노테이션
     */
    private void checkLogin(JoinPoint joinPoint, CheckLogin annotation) {
        // 세션이 없거나 로그인하지 않은 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            // 동적인 returnUrl을 생성하기 위해 파라미터 이름과 값을 가져옵니다.
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();

            // CheckLogin 어노테이션 객체의 returnUrl 값을 가져와 ${파라미터 이름} 형식으로 작성된 파라미터 값으로 치환합니다.
            // ex) /gallery/update?gseq=${gseq} -> /gallery/update?gseq=1
            String returnUrl = annotation.value();
            for (int i = 0; i < paramNames.length; i++) {
                String replacement = "${" + paramNames[i] + "}";
                if (returnUrl.contains(replacement)) {
                    returnUrl = returnUrl.replace(replacement, args[i].toString());
                }
            }

            // NotLoginException 예외를 발생시킵니다.
            throw new NotLoginException(memberService.getRedirectToLogin(returnUrl));
        }
    }

}