package com.team4.museum.controller.action.qna;

import static com.team4.museum.controller.action.qna.QnaAccessValidator.PERSONAL;
import static com.team4.museum.controller.action.qna.QnaAccessValidator.getValidatedQna;
import static com.team4.museum.controller.action.qna.QnaPwdCheckAjaxAction.savePwdCheckLog;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.QnaDao;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.QnaVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class QnaWriteAjaxAction extends AjaxAction {

	protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response) {
		// 'PERSONAL' 접근 조건을 만족하는 문의글 정보를 가져옴
		QnaVO qnaVO = getValidatedQna(request, PERSONAL);

		// 새로운 문의글인지 확인
		boolean isNew = qnaVO == null;
		if (isNew) {
			// 새로운 문의글인 경우 새로운 'QnaVO' 객체를 생성
			qnaVO = new QnaVO();
		}

		// 'QnaVO' 객체에 파라미터 값을 저장
		qnaVO.setTitle(request.getParameter("title"));
		qnaVO.setContent(request.getParameter("content"));
		qnaVO.setEmail(request.getParameter("email"));
		qnaVO.setPhone(request.getParameter("phone"));
		qnaVO.setPublic(request.getParameter("publicyn") != null && request.getParameter("publicyn").equals("on"));
		qnaVO.setPwd(request.getParameter("pwd"));

		// 새로운 문의글인 경우 'QnaDao' 객체를 이용해 문의글을 등록하고, 아니면 수정
		QnaDao dao = QnaDao.getInstance();
		int qseq = isNew ? dao.insertQna(qnaVO) : dao.updateQna(qnaVO);

		// 세션에 비밀번호 확인 기록 저장
		savePwdCheckLog(request, qseq);

		// 돌아갈 페이지 정보와 함께 성공 메시지를 반환
		return created(isNew ? "문의글이 등록되었습니다" : "문의글이 수정되었습니다", "museum.do?command=qnaView&qseq=" + qseq);
	}

}
