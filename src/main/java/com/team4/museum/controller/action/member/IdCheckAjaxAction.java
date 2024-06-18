package com.team4.museum.controller.action.member;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IdCheckAjaxAction extends AjaxAction {

	protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
			throws AjaxException {
		// 아이디 중복 확인에 필요한 정보를 받아옴 (id)
		// mustGetString() 호출 시 요청한 파라미터가 없으면 AjaxException을 던짐
		MemberVO mvo = MemberDao.getInstance().getMember(mustGetString("id"));

		// 입력된 'id'에 해당하는 사용자 계정이 있는 경우
		if (mvo != null) {
			return badRequest("이미 사용중인 아이디입니다");
		}

		// 아니면 성공 메시지를 반환 (ok 대신 created를 사용해 알림 메시지가 뜨도록 함)
		return created("사용 가능한 아이디입니다");
	}

}
