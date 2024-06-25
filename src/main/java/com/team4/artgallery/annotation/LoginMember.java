package com.team4.artgallery.annotation;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.resolver.LoginMemberArgumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인한 회원 정보를 주입받을 때 사용하는 어노테이션으로 {@link MemberDto} 타입의 파라미터에 사용합니다.
 * <p>
 * 로그인 페이지로 이동하는 기능이 존재하지 않기 때문에 {@link CheckLogin} 어노테이션과 함께 사용하는 것을 권장합니다.
 * <p>
 * 매개변수 주입은 {@link LoginMemberArgumentResolver} 에서 처리합니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface LoginMember {
}