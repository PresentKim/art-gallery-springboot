package com.team4.museum.controller.action.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.museum.dao.QnaDao;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

import static com.team4.museum.controller.action.member.LoginAjaxAction.isAdmin;
import static com.team4.museum.controller.action.qna.QnaPwdCheckAjaxAction.isAlreadyPwdChecked;

@FunctionalInterface
interface QnaAccessValidator {
    boolean validate(@Nonnull QnaDto qnaDto, HttpServletRequest request);

    // 기본 접근 권한 검사자

    /**
     * 개인 : 작성자만 접근 가능 (글 수정 등)
     */
    QnaAccessValidator PERSONAL = (v, r) -> isAlreadyPwdChecked(r, v.getQseq());

    /**
     * 허가 : 작성자 및 관리자만 접근 가능 (글 삭제 등)
     */
    QnaAccessValidator PRIVILEGE = (v, r) -> PERSONAL.validate(v, r) || isAdmin(r);

    /**
     * 제한 : 공개글이거나 {허가}된 접근 가능 (글 조회 등)
     */
    QnaAccessValidator RESTRICT = (v, r) -> PRIVILEGE.validate(v, r) || v.isPublic();

    /**
     * 주어진 접근 조건을 만족하는 문의글 정보를 가져옴
     *
     * @param request   요청 객체
     * @param validator 접근 조건 검사자
     * @return 접근 조건을 만족하는 문의글 정보
     */
    static QnaDto getValidatedQna(HttpServletRequest request, QnaAccessValidator validator) {
        // 'qseq' 파라미터가 없는 경우
        String qseqParam = request.getParameter("qseq");
        if (qseqParam == null || qseqParam.isEmpty() || !qseqParam.matches("^[0-9]*$")) {
            return null;
        }

        // 'qseq'에 해당하는 문의글 정보가 없는 경우
        QnaDto qnaDto = QnaDao.getInstance().getQna(Integer.parseInt(qseqParam));
        if (qnaDto == null) {
            return null;
        }

        // 'qnaVO'가 필터링 조건을 만족하지 않는 경우
        if (!validator.validate(qnaDto, request)) {
            return null;
        }

        // 'qnaVO'를 반환
        return qnaDto;
    }
}