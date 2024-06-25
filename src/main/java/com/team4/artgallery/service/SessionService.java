package com.team4.artgallery.service;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * HttpSession 객체는 웹 서버와 클라이언트 간의 세션을 관리하는 데 사용됩니다.
 * Spring에서 @Autowired를 통해 HttpSession 객체를 주입받아 사용할 수 있지만, 동일한 HttpSession 객체가 여러 요청에서 공유될 수 있습니다.
 * <p>
 * 이를 해결하기 위해 SessionService 클래스를 Session 스코프로 설정할 수 있습니다.
 * 이렇게 하면 각 세션마다 새로운 SessionService 인스턴스가 생성되고, 각 인스턴스는 자신의 HttpSession 객체를 가집니다.
 * <p>
 * session 스코프의 bean을 singleton 스코프의 bean에 주입하려면 proxyMode 속성을 ScopedProxyMode.TARGET_CLASS로 설정해야 합니다.
 * 이를 통해 실제 bean 대신 프록시를 통해 'session' 스코프의 빈에 액세스하게 되며, 이 프록시는 현재 HTTP 세션에 대한 'session' 스코프의 빈 인스턴스를 반환합니다.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SessionService {

    private final HttpSession session;

}