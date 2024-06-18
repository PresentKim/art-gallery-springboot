package com.team4.museum.controller.action.notice;

import java.io.IOException;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import com.team4.museum.util.Security;
import com.team4.museum.vo.NoticeVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateNoticeFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 권한이 없으면 404 페이지로 포워딩
		if (!Security.adminOr404Forward(request, response)) {
			return;
		}

		int nseq = Integer.parseInt(request.getParameter("nseq"));
		NoticeVO nvo = NoticeDao.getInstance().getNotice(nseq);
		
		// 소식지 정보가 없으면 404 페이지로 포워딩
		if (!Security.trueOr404Forward(nvo != null, request, response)) {
			return;
		}

		request.setAttribute("noticeUpdate", nvo);
		request.getRequestDispatcher("/WEB-INF/views/notice/updateNoticeForm.jsp").forward(request, response);
	}

}
