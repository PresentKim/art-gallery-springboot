package com.team4.museum.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

final public class UrlUtil {

    /**
     * URL 인코딩 처리
     * <p>
     * 입력 받은 URL을 UTF-8로 인코딩하여 반환한다.
     *
     * @param url 인코딩할 URL
     * @return 인코딩된 URL
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return url;
        }
    }

    /**
     * URL 디코딩 처리
     * <p>
     * 입력 받은 URL을 UTF-8로 디코딩하여 반환한다.
     *
     * @param url 디코딩할 URL
     * @return 디코딩된 URL
     */
    public static String decode(String url) {
        try {
            return URLDecoder.decode(url, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return url;
        }
    }

    /**
     * 현재 페이지 URL 경로 반환
     * <p>
     * 현재 페이지의 URL 경로를 반환한다.
     *
     * @param request 요청 객체
     * @return URL 경로
     */
    public static String getUrlPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        return uri + (query == null ? "" : "?" + query);
    }

}