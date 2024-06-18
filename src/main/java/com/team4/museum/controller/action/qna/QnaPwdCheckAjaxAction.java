package com.team4.museum.controller.action.qna;

import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.QnaDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.QnaVO;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class QnaPwdCheckAjaxAction extends AjaxAction {

    private final Map<String, SubAjaxAction> subActions = new HashMap<>();

    public QnaPwdCheckAjaxAction() {
        super();

        // 서브 액션들을 등록
        subActions.put("view", new SubAjaxAction(QnaAccessValidator.RESTRICT,
                (v, r) -> ok("접근이 허용되었습니다", "museum.do?command=qnaView&qseq=" + v.getQseq())));

        subActions.put("edit", new SubAjaxAction(QnaAccessValidator.PERSONAL,
                (v, r) -> ok("접근이 허용되었습니다", "museum.do?command=qnaWriteForm&qseq=" + v.getQseq())));

        subActions.put("delete", new SubAjaxAction(QnaAccessValidator.PRIVILEGE,
                (v, r) -> {
                    try {
                        QnaDao.getInstance().deleteQna(v.getQseq());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return internalServerError("문의글 삭제에 실패했습니다");
                    }
                    return created("문의글이 삭제되었습니다", "museum.do?command=qnaList");
                }));
    }

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws AjaxException {
        // 로그인에 필요한 정보를 받아옴 (mode, qseq)
        // mustGetString() 호출 시 요청한 파라미터가 없으면 AjaxException을 던짐
        String mode = mustGetString("mode");
        int qseq = mustGetInt("qseq");

        // 'mode'에 해당하는 서브 액션이 없는 경우
        SubAjaxAction subAction = subActions.get(mode);
        if (subAction == null) {
            return badRequest("잘못된 요청입니다");
        }

        // 입력된 'qseq'에 해당하는 문의글이 없는 경우
        QnaDao qdao = QnaDao.getInstance();
        QnaVO qnaVO = qdao.getQna(qseq);
        if (qnaVO == null) {
            return noContent("해당 문의가 존재하지 않습니다");
        }

        // subAction의 접근 조건을 만족하지 않는 경우
        if (!subAction.validate(qnaVO, request)) {
            // 'pwd' 파라미터가 없는 경우
            String pwd = request.getParameter("pwd");
            if (pwd == null || pwd.trim().isEmpty()) {
                return unauthorized("비밀번호를 입력해주세요");
            }

            // 비밀번호가 일치하지 않는 경우
            if (!qnaVO.getPwd().equals(pwd)) {
                return badRequest("비밀번호가 일치하지 않습니다");
            }

            // 세션에 비밀번호 확인 기록 저장
            savePwdCheckLog(request, qseq);
        }

        // ResultMapper로 결과를 생성해 반환
        return subAction.execute(qnaVO, request);
    }

    /**
     * 세션에 문의글 비밀번호 확인 기록이 있는지 확인합니다.
     *
     * @param request 세션 객체
     * @param qseq   문의글 번호
     * @return 비밀번호 확인 기록이 있으면 true, 없으면 false
     */
    public static boolean isAlreadyPwdChecked(HttpServletRequest request, int qseq) {
        return request.getSession().getAttribute("qnaPass" + qseq) != null;
    }

    /**
     * 세션에 문의글 비밀번호 확인 기록을 남깁니다.
     *
     * @param request 세션 객체
     * @param qseq   문의글 번호
     */
    public static void savePwdCheckLog(HttpServletRequest request, int qseq) {
        request.getSession().setAttribute("qnaPass" + qseq, true);
    }

    /**
     * 서브 액션은 접근 조건 검사자와 요청 처리자를 가지고 있습니다. <br/>
     * <p>
     * 접근 조건 검사자는 주어진 문의글 정보와 요청 객체를 이용해 접근 가능 여부를 검사합니다. <br/>
     * <p>
     * 요청 처리자는 주어진 문의글 정보와 요청 객체를 이용해 요청을 처리하고 결과를 반환합니다.
     */
    private static class SubAjaxAction {
        public QnaAccessValidator validator;
        public RequestMapper mapper;

        public SubAjaxAction(QnaAccessValidator validator, RequestMapper mapper) {
            this.validator = validator;
            this.mapper = mapper;
        }

        public boolean validate(@Nonnull QnaVO qnaVO, HttpServletRequest request) {
            return validator.validate(qnaVO, request);
        }

        public AjaxResult execute(@Nonnull QnaVO qnaVO, HttpServletRequest request) {
            return mapper.execute(qnaVO, request);
        }

        @FunctionalInterface
        private interface RequestMapper {
            AjaxResult execute(@Nonnull QnaVO qnaVO, HttpServletRequest request);
        }
    }
}
