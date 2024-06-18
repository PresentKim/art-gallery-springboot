package com.team4.museum.controller.action.member;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutAjaxAction extends AjaxAction {

	protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
			throws AjaxException {
		// 로그인한 사용자 정보를 세션에서 제거
		request.getSession().removeAttribute("loginUser");

		// 돌아갈 페이지 정보와 함께 성공 메시지를 반환
		return created("로그아웃 되었습니다", getReturnUrl());
	}

}
