package com.team4.artgallery.aspect.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인하지 않은 사용자가 로그인이 필요한 기능에 접근할 때 발생하는 예외 클래스
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotLoginException extends RuntimeException {

    private String returnUrl;

}
