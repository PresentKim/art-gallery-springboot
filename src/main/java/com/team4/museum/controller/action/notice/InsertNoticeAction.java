package com.team4.museum.controller.action.notice;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.util.Security;
import com.team4.museum.vo.MemberVO;
import com.team4.museum.vo.NoticeVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.team4.museum.controller.action.member.LoginAjaxAction.getLoginUser;

public class InsertNoticeAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        // 로그인 정보가 없으면 로그인 페이지로 이동
        MemberVO mvo = getLoginUser(request, response);
        if (mvo == null) {
            return;
        }

        NoticeVO nvo = new NoticeVO();
        nvo.setTitle(request.getParameter("title"));
        nvo.setAuthor(mvo.getId());
        nvo.setContent(request.getParameter("content"));
        nvo.setCategory(request.getParameter("category"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/notice");
        nvo.setImage(info.getFileName());
        nvo.setSavefilename(info.getSaveFileName());

        NoticeDao.getInstance().insertNotice(nvo);
        response.sendRedirect("museum.do?command=noticeList");
    }

}
