package com.team4.museum.controller.action.notice;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.enums.NoticeCategory;
import com.team4.artgallery.util.Pagination;
import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.NoticeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class NoticeListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        NoticeDao ndao = NoticeDao.getInstance();
        String category = request.getParameter("category");

        Pagination pagination = Pagination.with(request, 0, "command=noticeList&category=" + category);
        List<NoticeDto> noticeList = ndao.selectNoticeList(pagination);
        if (category == null || category.equals(NoticeCategory.전체.name())) {// 전체목록 조회
            pagination.setItemCount(ndao.getAllCount());
            noticeList = ndao.selectNoticeList(pagination);
        } else if (category.equals(NoticeCategory.매거진.name())) {
            request.getRequestDispatcher("/WEB-INF/views/notice/noticeMagazine.jsp").forward(request, response);
            return;
        } else if (category.equals(NoticeCategory.신문.name())) {
            request.getRequestDispatcher("/WEB-INF/views/notice/noticeNewspaper.jsp").forward(request, response);
            return;
        } else { // 카테고리 조회
            pagination.setItemCount(ndao.getNoticeCount(category));
            noticeList = ndao.selectCategoryNotice(category, pagination);
        }

        request.setAttribute("categoryName", category);
        request.setAttribute("noticeList", noticeList);
        request.getRequestDispatcher("/WEB-INF/views/notice/noticeList.jsp").forward(request, response);

    }

}
