package com.team4.museum.controller.action;

import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.dao.NoticeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class IndexAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NoticeDao ndao = NoticeDao.getInstance();
        ArtworkDao adao = ArtworkDao.getInstance();

        // 최근 공지사항 5개 불러오기
        List<NoticeDto> noticeList = ndao.getRecentNotice();
        request.setAttribute("noticeList", noticeList);

        // 랜덤 예술품 24개 불러오기 (4행 6열)
        List<ArtworkDto> randomArtworkList = adao.getRandomList(24);
        for (int i = 0; i < 4; ++i) {
            request.setAttribute("artworkList" + (i + 1), randomArtworkList.subList(i * 6, i * 6 + 6));
        }

        // main.jsp로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/main/main.jsp").forward(request, response);
    }

}
