package com.team4.museum.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;

final public class UrlUtil {

	/**
	 * URL 인코딩 처리
	 * 
	 * 입력 받은 URL을 UTF-8로 인코딩하여 반환한다.
	 * 
	 * @param url 인코딩할 URL
	 * @return
	 */
	public static String encode(String url) {
		try {
			return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			return url;
		}
	}

	/**
	 * URL 디코딩 처리
	 * 
	 * 입력 받은 URL을 UTF-8로 디코딩하여 반환한다.
	 * 
	 * @param url 디코딩할 URL
	 * @return
	 */
	public static String decode(String url) {
		try {
			return URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			return url;
		}
	}

	/**
	 * 현재 페이지 URL 경로 반환
	 * 
	 * 현재 페이지의 URL 경로를 반환한다.
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrlPath(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		return uri + (query == null ? "" : "?" + query);
	}

}