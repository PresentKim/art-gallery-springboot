package com.team4.museum.controller.action.artwork;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.Security;
import com.team4.museum.vo.ArtworkVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ArtworkUpdateAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        int aseq = Integer.parseInt(request.getParameter("aseq"));
        ArtworkVO avo = ArtworkDao.getInstance().get(aseq);

        // 예술품 정보가 없으면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(avo != null, request, response)) {
            return;
        }

        request.setAttribute("artwork", avo);
        request.getRequestDispatcher("/WEB-INF/views/artwork/artworkUpdateForm.jsp").forward(request, response);
    }

}
