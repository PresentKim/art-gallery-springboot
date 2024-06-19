package com.team4.museum.controller.action.admin;

import com.team4.artgallery.dto.MemberGalleryDto;
import com.team4.artgallery.util.Pagination;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberGalleryDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class AdminGalleryListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        MemberGalleryDao mgdao = MemberGalleryDao.getInstance();
        Pagination pagination = Pagination.with(request, mgdao.getAllCount(), "command=adminGalleryList");
        String searchWord = request.getParameter("searchWord");

        List<MemberGalleryDto> galleryList;
        if (searchWord != null) {
            pagination.setItemCount(mgdao.getSearchCount(searchWord));
            pagination.setUrlTemplate("museum.do?command=adminGalleryList&page=%d&searchWord=" + searchWord);
            galleryList = mgdao.searchGallery(pagination, searchWord);
            request.setAttribute("searchWord", searchWord);
        } else {
            galleryList = mgdao.getAllGallery(pagination);
        }

        request.setAttribute("galleryList", galleryList);
        request.getRequestDispatcher("/WEB-INF/views/admin/adminGalleryList.jsp").forward(request, response);
    }

}
