package com.team4.museum.controller.action.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.museum.controller.action.Action;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.team4.museum.controller.action.qna.QnaAccessValidator.RESTRICT;
import static com.team4.museum.controller.action.qna.QnaAccessValidator.getValidatedQna;

public class QnaViewAction implements Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 'RESTRICT' 접근 조건을 만족하는 문의글 정보를 가져옴
        QnaDto qnaDto = getValidatedQna(request, RESTRICT);

        // 'qnaDto'가 null 이면 404 페이지로 포워딩
        if (!Security.trueOr404Forward(qnaDto != null, request, response)) {
            return;
        }

        // 'qnaDto'를 'qnaView.jsp'로 전달
        request.setAttribute("qnaDto", qnaDto);
        request.getRequestDispatcher("/WEB-INF/views/qna/qnaView.jsp").forward(request, response);
    }

}
