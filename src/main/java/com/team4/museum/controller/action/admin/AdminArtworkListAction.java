package com.team4.museum.controller.action.admin;

import com.team4.artgallery.enums.ArtworkCategory;
import com.team4.artgallery.util.Pagination;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.ArtworkDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminArtworkListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        // 요청의 파라미터를 받아옵니다.
        String category = request.getParameter("category");
        String displayState = request.getParameter("displayState");
        String searchWord = request.getParameter("searchWord");

        // 요청의 속성으로 저장합니다.
        request.setAttribute("category", category);
        request.setAttribute("displayState", displayState);
        request.setAttribute("searchWord", searchWord);

        // 빈 파라미터를 빈 문자열로 치환합니다.
        if (category == null || ArtworkCategory.전체.name().equals(category)) {
            category = "";
        }
        if (searchWord == null) {
            searchWord = "";
        }
        if (displayState == null) {
            displayState = "";
        }

        // 예술품 목록 및 페이지네이션을 저장합니다.
        ArtworkDao adao = ArtworkDao.getInstance();
        Pagination pagination = Pagination.with(
                request,
                adao.getCount(category, displayState, searchWord),
                "command=adminArtworkList"
                        + "&artworkCategory=" + category
                        + "&displayState=" + displayState
                        + "&searchWord=" + searchWord);

        request.setAttribute("artworkList", adao.getAll(category, displayState, searchWord, pagination));
        request.getRequestDispatcher("/WEB-INF/views/admin/adminArtworkList.jsp").forward(request, response);
    }

}
