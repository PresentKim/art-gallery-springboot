package com.team4.museum.controller.action.gallery;

import com.team4.artgallery.dto.MemberDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.util.AccountUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GalleryWriteAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (AccountUtil.isLogined(request, response)) {
            MemberDto memberDto = AccountUtil.getLoginUserFrom(request);
            request.setAttribute("memberDto", memberDto);
            request.getRequestDispatcher("/WEB-INF/views/gallery/galleryWriteForm.jsp").forward(request, response);
        }
    }

}
