package com.team4.museum.controller.action.member;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JoinAjaxAction extends AjaxAction {

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws AjaxException {
        // 회원가입에 필요한 정보를 받아옴 (name, id, pwd, email, phone)
        // mustGetString() 호출 시 요청한 파라미터가 없으면 AjaxException을 던짐
        MemberVO mvo = new MemberVO();
        mvo.setName(mustGetString("name"));
        mvo.setId(mustGetString("id"));
        mvo.setPwd(mustGetString("pwd"));
        mvo.setEmail(mustGetString("email"));
        mvo.setPhone(mustGetString("phone"));

        // 회원가입에 실패한 경우
        if (MemberDao.getInstance().insertMember(mvo) == 0) {
            return internalServerError("회원가입에 실패하였습니다");
        }

        // 돌아갈 페이지 정보와 함께 성공 메시지를 반환
        return created("회원가입이 완료되었습니다", getReturnUrl());
    }

}
