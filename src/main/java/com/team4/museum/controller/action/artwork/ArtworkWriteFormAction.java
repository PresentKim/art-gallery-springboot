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

        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setName(request.getParameter("artname"));
        artworkDto.setCategory(request.getParameter("category"));
        artworkDto.setArtist(request.getParameter("artist"));
        artworkDto.setYear(request.getParameter("year"));
        artworkDto.setMaterial(request.getParameter("material"));
        artworkDto.setSize(request.getParameter("size"));
        artworkDto.setDisplayyn(request.getParameter("displayYn"));
        artworkDto.setContent(request.getParameter("content"));

        MultipartFileInfo info = MultipartFileInfo.getFromRequest(request, "static/image/artwork");
        artworkDto.setImage(info.getFileName());
        artworkDto.setSavefilename(info.getSaveFileName());

        ArtworkDao.getInstance().insertArtwork(artworkDto);
        response.sendRedirect("museum.do?command=artworkList");
    }

}
