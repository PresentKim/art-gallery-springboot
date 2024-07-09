package com.team4.artgallery.aspect;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.aspect.exception.NotLoginException;
import com.team4.artgallery.service.MemberService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 로그인 여부을 확인하는 Aspect 클래스
 *
 * @apiNote {@link CheckLogin} 어노테이션이 붙은 메서드나 클래스의 메소드가 호출될 때 로그인 여부를 확인합니다.
 * <p>
 * 세션 정보가 없거나 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
 */
@Aspect
@Component
public class CheckLoginAspect {

    private final MemberService memberService;

    public CheckLoginAspect(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * {@code @annotation}을 통해 메서드에 붙은 {@link CheckLogin} 어노테이션을 받아
     * {@link #checkLogin(JoinPoint, CheckLogin)} 메서드에 전달합니다.
     *
     * @param joinPoint AOP가 적용된 메서드의 정보
     * @implNote {@link Before} 어노테이션으로 메서드 실행 전에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @annotation}은 메서드에 붙은 어노테이션을 찾아내는데 사용됩니다.
     */
    @Before("@annotation(annotation)")
    public void onAnnotation(JoinPoint joinPoint, CheckLogin annotation) {
        checkLogin(joinPoint, annotation);
    }

    /**
     * {@code @within}을 통해 클래스에 붙은 {@link CheckLogin} 어노테이션을 받아
     * {@link #checkLogin(JoinPoint, CheckLogin)} 메서드에 전달합니다.
     *
     * @param joinPoint AOP가 적용된 메서드의 정보
     * @implNote {@link Before} 어노테이션으로 메서드 실행 전에 로직을 추가할 수 있습니다.
     * <p>
     * {@code value}값의 {@code @within}은 클래스에 붙은 어노테이션을 찾아내는데 사용됩니다.
     */
    @Before("@within(annotation)")
    public void onWithin(JoinPoint joinPoint, CheckLogin annotation) {
        checkLogin(joinPoint, annotation);
    }

    /**
     * CheckLogin 어노테이션을 처리하는 메서드입니다.
     * <p>
     * 로그인하지 않은 경우 {@link NotLoginException} 예외를 발생시킵니다.
     */
    private void checkLogin(JoinPoint joinPoint, CheckLogin annotation) throws NotLoginException {
        if (!memberService.isLogin()) {
            // 동적인 returnUrl을 생성하기 위해 파라미터 이름과 값을 가져옵니다.
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();

            // CheckLogin 어노테이션 객체의 returnUrl 값을 가져와 ${파라미터 이름} 형식으로 작성된 파라미터 값으로 치환합니다.
            // ex) /gallery/update/${gseq} -> /gallery/update/1
            String returnUrl = annotation.value();
            for (int i = 0; i < paramNames.length; i++) {
                String replacement = "${" + paramNames[i] + "}";
                if (returnUrl.contains(replacement)) {
                    returnUrl = returnUrl.replace(replacement, args[i].toString());
                }
            }

            throw new NotLoginException(memberService.getRedirectToLogin(returnUrl));
        }
    }

}