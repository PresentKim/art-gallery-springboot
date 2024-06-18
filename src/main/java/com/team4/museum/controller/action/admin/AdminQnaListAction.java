package com.team4.museum.controller.action.admin;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.QnaDao;
import com.team4.museum.util.Pagination;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminQnaListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        QnaDao qdao = QnaDao.getInstance();
        String isReply = request.getParameter("isReply");
        String searchWord = request.getParameter("searchWord");

        Pagination pagination = Pagination.with(request, qdao.getAllCount(), "command=adminQnaList");
        if (searchWord != null) {
            pagination.setItemCount(qdao.getSearchCount(searchWord));
            pagination.setUrlTemplate("museum.do?command=adminQnaList&page=%d&searchWord=" + searchWord);
            request.setAttribute("qnaList", qdao.searchQna(pagination, searchWord));

        } else if (isReply != null) {
            if (isReply.equals("Y")) {
                pagination.setItemCount(qdao.getReplyCount());
            } else {
                pagination.setItemCount(qdao.getAllCount() - qdao.getReplyCount());
            }
            pagination.setUrlTemplate("museum.do?command=adminQnaList&page=%d&isReply=" + isReply);
            request.setAttribute("qnaList", qdao.selectQna(pagination, isReply));
            request.setAttribute("isReply", isReply);
        } else {
            request.setAttribute("qnaList", qdao.selectQna(pagination));
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/adminQnaList.jsp").forward(request, response);
    }

}
