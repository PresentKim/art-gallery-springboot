package com.team4.museum.controller.action.notice;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.util.Security;
import com.team4.museum.vo.MemberVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.team4.museum.controller.action.member.LoginAjaxAction.getLoginUser;

public class UpdateNoticeAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        // 로그인 정보가 없는 경우 로그인 페이지로 이동
        MemberVO mvo = getLoginUser(request, response);
        if (mvo == null) {
            return;
        }

        NoticeDao ndao = NoticeDao.getInstance();
        NoticeDto nvo = new NoticeDto();
        nvo.setNseq(Integer.parseInt(request.getParameter("nseq")));
        nvo.setAuthor(mvo.getId());
        nvo.setTitle(request.getParameter("title"));
        nvo.setContent(request.getParameter("content"));
        nvo.setCategory(request.getParameter("category"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/notice");
        if (!info.isEmpty()) {
            nvo.setImage(info.getFileName());
            nvo.setSavefilename(info.getSaveFileName());
        }

        ndao.updateNotice(nvo);
        response.sendRedirect("museum.do?command=noticeViewWithoutCnt&nseq=" + nvo.getNseq());
    }

}
