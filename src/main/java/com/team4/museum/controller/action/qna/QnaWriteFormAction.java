package com.team4.museum.controller.action.qna;

import com.team4.museum.controller.action.Action;
import com.team4.museum.vo.QnaVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.team4.museum.controller.action.qna.QnaAccessValidator.PERSONAL;
import static com.team4.museum.controller.action.qna.QnaAccessValidator.getValidatedQna;

public class QnaWriteFormAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 'PERSONAL' 접근 조건을 만족하는 문의글 정보를 가져옴
        QnaVO qnaVO = getValidatedQna(request, PERSONAL);

        // 'qnaVO'를 'qnaView.jsp'로 전달
        request.setAttribute("qnaVO", qnaVO);
        request.getRequestDispatcher("/WEB-INF/views/qna/qnaWriteForm.jsp").forward(request, response);
    }

}
