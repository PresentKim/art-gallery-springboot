package com.team4.museum.controller.action.gallery;

import com.team4.museum.controller.action.Action;
import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.vo.MemberGalleryVO;
import com.team4.museum.vo.MemberVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryWriteFormAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 로그인 정보가 없으면 로그인 페이지로 이동
        MemberVO mvo = LoginAjaxAction.getLoginUser(request, response);
        if (mvo == null) {
            return;
        }

        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();
        MemberGalleryVO mgvo = new MemberGalleryVO();
        mgvo.setTitle(request.getParameter("title"));
        mgvo.setContent(request.getParameter("content"));
        mgvo.setAuthorId(mvo.getId());

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/gallery");
        mgvo.setImage(info.getFileName());
        mgvo.setSavefilename(info.getSaveFileName());

        mgdao.insertMemberGallery(mgvo);
        response.sendRedirect("museum.do?command=galleryList");

    }

}