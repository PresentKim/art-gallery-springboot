package com.team4.museum.controller.action.member;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WithdrawAjaxAction extends AjaxAction {

	protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
			throws AjaxException {
		// 로그인한 사용자 정보를 가져옴
		MemberVO mvo = mustGetLoginUser();
		String pwd = mustGetString("pwd");

		// 비밀번호가 일치하지 않으면 경고 메시지를 반환
		if (!mvo.getPwd().equals(pwd)) {
			return badRequest("비밀번호가 일치하지 않습니다");
		}

		// 회원 탈퇴 처리
		MemberDao.getInstance().deleteMember(mvo.getId());

		// 로그인한 사용자 정보를 세션에서 제거
		request.getSession().removeAttribute("loginUser");

		// 돌아갈 페이지 정보와 함께 성공 메시지를 반환
		return created("회원 탈퇴 되었습니다", getReturnUrl());
	}

}
