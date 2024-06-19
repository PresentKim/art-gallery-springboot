package com.team4.museum.controller.action.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.QnaDao;
import com.team4.museum.util.ajax.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.team4.museum.controller.action.qna.QnaAccessValidator.PERSONAL;
import static com.team4.museum.controller.action.qna.QnaAccessValidator.getValidatedQna;
import static com.team4.museum.controller.action.qna.QnaPwdCheckAjaxAction.savePwdCheckLog;

public class QnaWriteAjaxAction extends AjaxAction {

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response) {
        // 'PERSONAL' 접근 조건을 만족하는 문의글 정보를 가져옴
        QnaDto qnaDto = getValidatedQna(request, PERSONAL);

        // 새로운 문의글인지 확인
        boolean isNew = qnaDto == null;
        if (isNew) {
            // 새로운 문의글인 경우 새로운 'QnaVO' 객체를 생성
            qnaDto = new QnaDto();
        }

        // 'QnaVO' 객체에 파라미터 값을 저장
        qnaDto.setTitle(request.getParameter("title"));
        qnaDto.setContent(request.getParameter("content"));
        qnaDto.setEmail(request.getParameter("email"));
        qnaDto.setPhone(request.getParameter("phone"));
        qnaDto.setPublic(request.getParameter("publicyn") != null && request.getParameter("publicyn").equals("on"));
        qnaDto.setPwd(request.getParameter("pwd"));

        // 새로운 문의글인 경우 'QnaDao' 객체를 이용해 문의글을 등록하고, 아니면 수정
        QnaDao dao = QnaDao.getInstance();
        int qseq = isNew ? dao.insertQna(qnaDto) : dao.updateQna(qnaDto);

        // 세션에 비밀번호 확인 기록 저장
        savePwdCheckLog(request, qseq);

        // 돌아갈 페이지 정보와 함께 성공 메시지를 반환
        return created(isNew ? "문의글이 등록되었습니다" : "문의글이 수정되었습니다", "museum.do?command=qnaView&qseq=" + qseq);
    }

}
