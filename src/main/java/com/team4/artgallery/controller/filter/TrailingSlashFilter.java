package com.team4.artgallery.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 요청 URL 마지막 슬래시를 제거하고 리다이렉트하는 필터
 */
@WebFilter("/*")
public class TrailingSlashFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (processRequest(request, response)) {
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * 요청 URL의 마지막 슬래시를 제거하고 리다이렉트합니다.
     *
     * @return 처리 여부
     */
    private boolean processRequest(ServletRequest request, ServletResponse response) throws IOException {
        // HttpServletRequest와 HttpServletResponse가 아닌 경우 처리하지 않습니다.
        if (!(request instanceof HttpServletRequest httpRequest) || !(response instanceof HttpServletResponse httpResponse)) {
            return false;
        }

        // 요청 URL의 마지막 글자가 슬래시가 아닌 경우 처리하지 않습니다.
        String requestURI = httpRequest.getRequestURI();
        if (!requestURI.endsWith("/") || requestURI.length() <= 1) {
            return false;
        }

        // 마지막 슬래시를 제거하고 리다이렉트합니다.
        String newURI = requestURI.substring(0, requestURI.length() - 1);
        httpResponse.sendRedirect(newURI);
        return true;
    }

}