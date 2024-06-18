package com.team4.museum.controller.action.artwork;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.util.Security;
import com.team4.museum.vo.ArtworkVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ArtworkUpdateFormAction implements Action {

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

        avo.setArtist(request.getParameter("artist"));
        avo.setName(request.getParameter("artname"));
        avo.setYear(request.getParameter("year"));
        avo.setMaterial(request.getParameter("material"));
        avo.setSize(request.getParameter("size"));
        avo.setCategory(request.getParameter("category"));
        avo.setDisplayyn(request.getParameter("displayYn"));
        avo.setContent(request.getParameter("content"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/artwork");
        if (!info.isEmpty()) {
            avo.setImage(info.getFileName());
            avo.setSavefilename(info.getSaveFileName());
        }

        ArtworkDao.getInstance().updateArtwork(avo);
        response.sendRedirect("museum.do?command=artworkView&aseq=" + aseq);
    }

}
