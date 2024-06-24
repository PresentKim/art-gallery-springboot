package com.team4.museum.controller.action.gallery;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryUpdateFormAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();

        int gseq = Integer.parseInt(request.getParameter("gseq"));
        GalleryDto galleryDto = mgdao.getMemberGalleryOne(gseq);

        // 갤러리 정보가 없으면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(galleryDto != null, request, response)) {
            return;
        }

        // 갤러리 주인이 아니면 경고창 스크립트만 발송
        if (!Security.memberEqualsOrAlert(galleryDto.getAuthorId(), request, response)) {
            return;
        }

        galleryDto.setGseq(gseq);
        galleryDto.setTitle(request.getParameter("title"));
        galleryDto.setContent(request.getParameter("content"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/gallery");
        if (!info.isEmpty()) {
            galleryDto.setImage(info.getFileName());
            galleryDto.setSavefilename(info.getSaveFileName());
        }

        mgdao.updateMemberGallery(galleryDto);
        response.sendRedirect("museum.do?command=galleryView&gseq=" + gseq);
    }

}
