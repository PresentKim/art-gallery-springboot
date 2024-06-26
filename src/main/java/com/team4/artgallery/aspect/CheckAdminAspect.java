package com.team4.artgallery.aspect;

import com.team4.artgallery.annotation.CheckAdmin;
import com.team4.artgallery.exception.NotAdminException;
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

    @Before(value = "@annotation(checkAdmin) || @within(checkAdmin)")
    public void checkAdmin(CheckAdmin checkAdmin) {
        // 세션이 없거나 관리자가 아닌 경우 예외 발생
        HttpSession session = sessionService.getSession();
        if (session == null || !memberService.isAdmin(session)) {
            throw new NotAdminException();
        }
    }

}