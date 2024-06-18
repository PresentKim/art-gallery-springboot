package com.team4.museum.controller.action.member.mypage;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyPageEditAjaxAction extends AjaxAction {

	protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
			throws AjaxException {
		// 정보 수정에 필요한 정보를 받아옴 (name, id, pwd, email, phone)
		// mustGetString() 호출 시 요청한 파라미터가 없으면 AjaxException을 던짐
		MemberVO mvo = mustGetLoginUser();
		mvo.setName(mustGetString("name"));
		mvo.setId(mustGetString("id"));
		mvo.setPwd(mustGetString("pwd"));
		mvo.setEmail(mustGetString("email"));
		mvo.setPhone(mustGetString("phone"));

		// 정보 수정에 실패한 경우
		if (MemberDao.getInstance().updateMember(mvo) == 0) {
			return internalServerError("정보 수정에 실패하였습니다");
		}

		// 돌아갈 페이지 정보와 함께 성공 메시지를 반환
		return created("정보 수정이 완료되었습니다", getReturnUrl());
	}

}