package com.team4.museum.controller.action.artwork;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.MultipartFileInfo;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ArtworkWriteFormAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        ArtworkDto avo = new ArtworkDto();
        avo.setName(request.getParameter("artname"));
        avo.setCategory(request.getParameter("category"));
        avo.setArtist(request.getParameter("artist"));
        avo.setYear(request.getParameter("year"));
        avo.setMaterial(request.getParameter("material"));
        avo.setSize(request.getParameter("size"));
        avo.setDisplayyn(request.getParameter("displayYn"));
        avo.setContent(request.getParameter("content"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/artwork");
        avo.setImage(info.getFileName());
        avo.setSavefilename(info.getSaveFileName());

        ArtworkDao.getInstance().insertArtwork(avo);
        response.sendRedirect("museum.do?command=artworkList");
    }

}
