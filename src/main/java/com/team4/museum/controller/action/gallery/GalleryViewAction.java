package com.team4.museum.controller.action.gallery;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.Security;
import com.team4.museum.vo.MemberGalleryVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryViewAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();

        int mseq = Integer.parseInt(request.getParameter("mseq"));
        MemberGalleryVO mgvo = mgdao.getMemberGalleryOne(mseq);

        // 갤러리 정보가 없으면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(mgvo != null, request, response)) {
            return;
        }

        // 조회수 1 증가
        mgdao.increaseReadCount(mseq);

        request.setAttribute("galleryVO", mgvo);
        request.getRequestDispatcher("/WEB-INF/views/gallery/galleryView.jsp").forward(request, response);
    }

}
