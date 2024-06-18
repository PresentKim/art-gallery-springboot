package com.team4.museum.controller.action.qna;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.QnaDao;
import com.team4.museum.util.Pagination;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class QnaListAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // QnaDao를 이용해 현재 페이지의 문의글 목록을 가져옴
        QnaDao qdao = QnaDao.getInstance();
        Pagination pagination = Pagination.with(request, qdao.getAllCount(), "command=qnaList");

        // 'qnaList'를 'qnaList.jsp'로 전달
        request.setAttribute("qnaList", qdao.selectQna(pagination));
        request.getRequestDispatcher("/WEB-INF/views/qna/qnaList.jsp").forward(request, response);
    }

}
