package com.team4.museum.controller.action.admin;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminDeleteGalleryAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        String[] mseqList = request.getParameter("memberIds").split(",");

        for (String mseq : mseqList) {
            MemberGalleryDao.getInstance().deleteMemberGallery(Integer.parseInt(mseq));
        }

        response.sendRedirect("museum.do?command=adminGalleryList");
    }

}
