package com.team4.museum.controller.action.gallery;

import java.io.IOException;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GalleryWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (LoginAjaxAction.isLogined(request, response)) {
			MemberVO mvo = LoginAjaxAction.getLoginUserFrom(request);
			request.setAttribute("mvo", mvo);
			request.getRequestDispatcher("/WEB-INF/views/gallery/galleryWriteForm.jsp").forward(request, response);
		}
	}

}
