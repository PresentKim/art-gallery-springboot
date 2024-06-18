package com.team4.museum.controller.action.artwork;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ArtworkDisplaySetAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        int aseq = Integer.parseInt(request.getParameter("aseq"));
        ArtworkDao.getInstance().displayChangeArtwork(aseq);

        response.sendRedirect("museum.do?command=artworkView&aseq=" + aseq);
    }

}
