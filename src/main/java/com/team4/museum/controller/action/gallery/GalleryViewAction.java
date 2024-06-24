package com.team4.museum.controller.action.gallery;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryViewAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();

        int gseq = Integer.parseInt(request.getParameter("gseq"));
        GalleryDto galleryDto = mgdao.getMemberGalleryOne(gseq);

        // 갤러리 정보가 없으면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(galleryDto != null, request, response)) {
            return;
        }

        // 조회수 1 증가
        mgdao.increaseReadCount(gseq);

        request.setAttribute("galleryDto", galleryDto);
        request.getRequestDispatcher("/WEB-INF/views/gallery/galleryView.jsp").forward(request, response);
    }

}
