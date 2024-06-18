package com.team4.museum.controller.action.member.mypage;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.FavoriteDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyPageFavoriteAjaxAction extends AjaxAction {

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws AjaxException {
        // 정보 수정에 필요한 정보를 받아옴 (aseq, loginUser) 필요한 데이터가 없으면 AjaxException을 던짐
        Boolean result = FavoriteDao.getInstance().toggleFavorite(
                mustGetLoginUser().getId(),
                mustGetInt("aseq"));

        // 즐겨찾기 추가/삭제 결과에 따라 메시지와 함께 성공 메시지를 반환
        return created(result ? "즐겨찾기 목록에 추가되었습니다" : "즐겨찾기 목록에서 삭제되었습니다");
    }

}
