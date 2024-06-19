package com.team4.museum.controller.action.notice;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NoticeViewWithoutCntAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int nseq = Integer.parseInt(request.getParameter("nseq"));
        NoticeDto noticeDto = NoticeDao.getInstance().getNotice(nseq);

        // 소식지 정보가 없으면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(noticeDto != null, request, response)) {
            return;
        }

        request.setAttribute("noticeView", noticeDto);
        request.getRequestDispatcher("/WEB-INF/views/notice/noticeView.jsp").forward(request, response);
    }

}
