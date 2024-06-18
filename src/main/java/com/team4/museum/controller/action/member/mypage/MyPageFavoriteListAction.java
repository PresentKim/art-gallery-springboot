package com.team4.museum.controller.action.member.mypage;

import static com.team4.museum.controller.action.member.LoginAjaxAction.getLoginUser;

import java.io.IOException;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.FavoriteDao;
import com.team4.museum.util.Pagination;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyPageFavoriteListAction implements Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 정보가 없으면 로그인 페이지로 이동
		MemberVO mvo = getLoginUser(request, response);
		if (mvo == null) {
			return;
		}

		FavoriteDao fdao = FavoriteDao.getInstance();
		String memberId = mvo.getId();
		Pagination pagination = Pagination.with(request, fdao.getCount(memberId), "command=mypageFavoriteList");

		request.setAttribute("artworkList", fdao.selectFavorite(memberId, pagination));
		request.getRequestDispatcher("/WEB-INF/views/member/mypage/mypageFavoriteList.jsp").forward(request, response);
	}

}
