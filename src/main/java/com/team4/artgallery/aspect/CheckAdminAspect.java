package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.aspect.exception.NotAdminException;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckAdminAspect {

    private final MemberService memberService;
    private final SessionService sessionService;


    /**
     * <code>@annotation</code>을 통해 메서드에 붙은 {@link CheckAdmin} 어노테이션을 받아
     * {@link #checkAdmin(CheckAdmin)} 메서드에 전달합니다.
     *
     * @implNote @Before(value = "@annotation(com.team4.artgallery.aspect.annotation.CheckAdmin)")
     * 위와 같은 방식으로 직접 클래스를 작성해도 되지만, "어노테이션 매개변수" 방식을 통해 사용할 수 있습니다.
     * 이 경우 해당 어노테이션 매개변수의 이름을 클래스 대신 사용하면 됩니다.
     * {@see https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/advice.html}
     */
    @Before(value = "@annotation(annotation)")
    public void onAnnotation(CheckAdmin annotation) {
        checkAdmin(annotation);
    }

    /**
     * <code>@within</code>을 통해 클래스에 붙은 {@link CheckAdmin} 어노테이션을 받아
     * {@link #checkAdmin(CheckAdmin)} 메서드에 전달합니다.
     *
     * @implNote @within() 은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     */
    @Before(value = "@within(annotation)")
    public void onWithin(CheckAdmin annotation) {
        checkAdmin(annotation);
    }

    /**
     * {@link CheckAdmin} 어노테이션을 처리하는 메서드입니다.
     * <p>
     * 세션이 없거나 관리자가 아닌 경우 {@link NotAdminException} 예외를 발생시킵니다.
     *
     * @param annotation CheckAdmin 어노테이션
     */
    private void checkAdmin(CheckAdmin annotation) {
        // 세션이 없거나 관리자가 아닌 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isAdmin(session)) {
            throw new NotAdminException();
        }
    }

}