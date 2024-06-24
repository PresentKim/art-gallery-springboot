package com.team4.museum.controller.action.gallery;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.AccountUtil;
import com.team4.museum.util.MultipartFileInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryWriteFormAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 로그인 정보가 없으면 로그인 페이지로 이동
        MemberDto memberDto = AccountUtil.getLoginUser(request, response);
        if (memberDto == null) {
            return;
        }

        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();
        GalleryDto galleryDto = new GalleryDto();
        galleryDto.setTitle(request.getParameter("title"));
        galleryDto.setContent(request.getParameter("content"));
        galleryDto.setAuthorId(memberDto.getId());

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/gallery");
        galleryDto.setImage(info.getFileName());
        galleryDto.setSavefilename(info.getSaveFileName());

        mgdao.insertMemberGallery(galleryDto);
        response.sendRedirect("museum.do?command=galleryList");

    }

}