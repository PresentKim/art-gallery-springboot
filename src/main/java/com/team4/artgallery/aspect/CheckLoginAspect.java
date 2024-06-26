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

    /*
     * @Before(value = "@annotation(com.team4.artgallery.aspect.annotation.CheckAdmin)")
     * 위와 같은 방식으로 직접 클래스를 작성해도 되지만, "어노테이션 매개변수" 방식을 통해 사용할 수 있습니다.
     * 이 경우 해당 어노테이션 매개변수의 이름을 클래스 대신 사용하면 됩니다. (pointcut expression)
     *
     * @annotation() 은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     * @within() 은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     */
    @Before(value = "@annotation(checkLogin) || @within(checkLogin)")
    public void checkLogin(JoinPoint joinPoint, CheckLogin checkLogin) {
        // 어노테이션이 null 인 경우예외 발생
        if (checkLogin == null) {
            throw new NotLoginException();
        }

        // 세션이 없거나 로그인하지 않은 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isLogin(session)) {
            // 동적인 returnUrl을 생성하기 위해 파라미터 이름과 값을 가져옵니다.
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
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