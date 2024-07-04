package com.team4.artgallery.service.helper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * 요청 스코프의 빈을 사용하여 요청 객체를 주입받아 제공하는 프록시 서비스
 *
 * @apiNote 아래와 같이 요청 객체가 필요하지만 요청 객체를 별도로 제공받지 못하는 상황에 사용하는 서비스입니다.
 *
 * <blockquote><pre>
 * {@code @Autowired}
 * private final RequestService requestService;
 *
 * private void example() {
 *     HttpServletRequest request = requestService.getRequest();
 *     // 요청 객체를 사용하는 코드
 * }
 * </pre></blockquote>
 * @implNote Spring에서 {@link HttpServletRequest} 객체는 HTTP 요청 정보가 담긴 객체입니다.
 * <p>{@link Autowired}를 통해 요청 객체를 주입받아 사용할 수 있지만,
 * 그냥 사용하면 동일한 요청 객체가 여러 요청에서 공유될 수 있습니다.
 * <p>
 * 이를 해결하기 위해 {@link RequestService} 클래스를 요청 스코프로 설정할 수 있습니다.
 * 이렇게 하면 각 요청마다 새로운 인스턴스가 생성되고, 각 인스턴스는 고유한 객체를 가집니다.
 * <p>
 * 요청 스코프의 빈을 싱글톤 스코프의 빈에 주입하려면 {@link Scope#proxyMode} 값을 {@link ScopedProxyMode#TARGET_CLASS}로 설정해야 합니다.
 * 이를 통해 실제 빈 대신 프록시를 통해 요청 스코프의 빈에 액세스하게 되며, 이 프록시는 현재 HTTP 요청에 대한 요청 스코프의 빈 인스턴스를 반환합니다.
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes"> 스프링 프레임워크 - Bean Scopes </a>
 */
@Service
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@RequiredArgsConstructor
public class RequestService {

    private final HttpServletRequest request;

}