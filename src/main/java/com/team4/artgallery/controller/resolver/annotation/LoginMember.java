package com.team4.artgallery.controller.resolver.annotation;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.LoginMemberArgumentResolver;
import com.team4.artgallery.entity.MemberEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인한 회원 정보({@link MemberEntity}) 객체를 파라미터로 주입받을 때 사용하는 어노테이션
 *
 * @apiNote {@link MemberEntity} 타입의 매개변수에 사용해 로그인한 회원 정보를 주입받을 수 있습니다.
 * <p>
 * 로그인하지 않은 경우 로그인 페이지로 이동하는 기능이 없기 때문에 필요한 경우 {@link CheckLogin}과 함께 사용하는 것을 권장합니다.
 * @implNote 매개변수 주입은 {@link LoginMemberArgumentResolver} 에서 처리합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface LoginMember {
}