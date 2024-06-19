package com.team4.museum.controller.action;

import com.team4.artgallery.util.UrlUtil;
import com.team4.museum.controller.action.member.LoginAjaxAction;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import com.team4.museum.vo.MemberVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;

abstract public class AjaxAction implements Action {

    private HttpServletRequest currentRequest;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AjaxResult result;

        try {
            // 유틸 메소드에서 사용하기 위해 현재 요청을 멤버 변수에 저장
            currentRequest = request;

            // AJAX 요청을 처리
            result = handleAjaxRequest(request, response);
        } catch (AjaxException e) {
            // 확인된 AJAX 예외를 처리
            result = new AjaxResult(e.getStatusCode(), e.getMessage(), e.getUrl());
        } catch (Exception e) {
            // 확인되지 않은 예외를 처리
            e.printStackTrace();
            result = internalServerError();
        } finally {
            // 현재 요청을 초기화
            currentRequest = null;
        }

        // AJAX 결과를 응답
        response.setStatus(result.code);
        response.setContentType("application/json");
        response.getWriter().write(result.toJson());
    }

    /**
     * AJAX 요청을 처리하는 핸들러 메소드
     *
     * @param request 현재 요청
     * @param response 현재 응답
     * @return AJAX 결과
     */
    abstract protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AjaxException;

    /**
     * OK(200) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult ok() {
        return ok("성공");
    }

    /**
     * OK(200) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult ok(String message) {
        return ok(message, "");
    }

    /**
     * OK(200) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult ok(String message, String url) {
        return new AjaxResult(SC_OK, message, url);
    }

    /**
     * CREATED(201) 알림과 함께 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult created() {
        return created("생성되었습니다");
    }

    /**
     * CREATED(201) 알림과 함께 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult created(String message) {
        return created(message, "");
    }

    /**
     * CREATED(201) 알림과 함께 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult created(String message, String url) {
        return new AjaxResult(SC_CREATED, message, url);
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult internalServerError() {
        return internalServerError("서버 오류가 발생했습니다");
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult internalServerError(String message) {
        return internalServerError(message, "");
    }

    /**
     * INTERNAL_SERVER_ERROR(500) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult internalServerError(String message, String url) {
        return new AjaxResult(SC_INTERNAL_SERVER_ERROR, message, url);
    }

    /**
     * BAD_REQUEST(400) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult badRequest() {
        return badRequest("잘못된 요청입니다");
    }

    /**
     * BAD_REQUEST(400) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult badRequest(String message) {
        return badRequest(message, "");
    }

    /**
     * BAD_REQUEST(400) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult badRequest(String message, String url) {
        return new AjaxResult(SC_BAD_REQUEST, message, url);
    }

    /**
     * UNAUTHORIZED(401) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult unauthorized() {
        return unauthorized("로그인이 필요합니다");
    }

    /**
     * UNAUTHORIZED(401) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult unauthorized(String message) {
        return unauthorized(message, "");
    }

    /**
     * UNAUTHORIZED(401) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult unauthorized(String message, String url) {
        return new AjaxResult(SC_UNAUTHORIZED, message, url);
    }

    /**
     * FORBIDDEN(403) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult forbidden() {
        return forbidden("접근 권한이 없습니다");
    }

    /**
     * FORBIDDEN(403) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult forbidden(String message) {
        return forbidden(message, "");
    }

    /**
     * FORBIDDEN(403) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult forbidden(String message, String url) {
        return new AjaxResult(SC_FORBIDDEN, message, url);
    }

    /**
     * NO_CONTENT(204) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult noContent() {
        return noContent("내용이 없습니다");
    }

    /**
     * NO_CONTENT(204) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult noContent(String message) {
        return noContent(message, "");
    }

    /**
     * NO_CONTENT(204) 알림 없이 URL로 이동되는 성공 코드를 반환
     */
    protected AjaxResult noContent(String message, String url) {
        return new AjaxResult(SC_NO_CONTENT, message, url);
    }

    /**
     * NOT_FOUND(404) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notFound() {
        return notFound("찾을 수 없습니다");
    }

    /**
     * NOT_FOUND(404) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notFound(String message) {
        return notFound(message, "");
    }

    /**
     * NOT_FOUND(404) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notFound(String message, String url) {
        return new AjaxResult(SC_NOT_FOUND, message, url);
    }

    /**
     * NOT_IMPLEMENTED(501) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notImplemented() {
        return notImplemented("구현되지 않은 요청입니다");
    }

    /**
     * NOT_IMPLEMENTED(501) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notImplemented(String message) {
        return notImplemented(message, "");
    }

    /**
     * NOT_IMPLEMENTED(501) 알림과 함께 URL로 이동되는 실패 코드를 반환
     */
    protected AjaxResult notImplemented(String message, String url) {
        return new AjaxResult(SC_NOT_IMPLEMENTED, message, url);
    }

    /**
     * 돌아갈 페이지 URL을 반환합니다. 없을 경우 기본값으로 index 페이지로 이동합니다.
     */
    protected String getReturnUrl() throws AjaxException {
        if (currentRequest == null) {
            throw new AjaxException(SC_INTERNAL_SERVER_ERROR, "메소드는 반드시 handleAjaxRequest 메소드 내에서만 사용해주세요");
        }

        String returnUrl = "museum.do?command=index";
        String returnUrlParam = (String) currentRequest.getParameter("returnUrl");
        if (returnUrlParam != null && !returnUrlParam.isEmpty()) {
            returnUrl = UrlUtil.decode(returnUrlParam);
        }

        return returnUrl;
    }

    /**
     * 요구되는 파라미터를 문자열로 반환합니다. 없을 경우 AjaxException을 발생시킵니다.
     */
    protected String mustGetString(String parameter) throws AjaxException {
        if (currentRequest == null) {
            throw new AjaxException(SC_INTERNAL_SERVER_ERROR, "메소드는 반드시 handleAjaxRequest 메소드 내에서만 사용해주세요");
        }

        String str = currentRequest.getParameter(parameter);
        if (str == null || str.equals("")) {
            throw new AjaxException(SC_BAD_REQUEST, "'" + parameter + "'를 입력해주세요");
        }
        return str;
    }

    /**
     * 요구되는 파라미터를 정수로 반환합니다. 없거나 정수가 아닐 경우 AjaxException을 발생시킵니다.
     */
    protected int mustGetInt(String parameter) throws AjaxException {
        try {
            return Integer.parseInt(mustGetString(parameter));
        } catch (NumberFormatException e) {
            throw new AjaxException(SC_BAD_REQUEST, "'" + parameter + "'는 숫자로 입력해주세요");
        }
    }

    /**
     * 로그인된 사용자 정보를 반환합니다. 로그인되어 있지 않을 경우 AjaxException을 발생시킵니다.
     */
    protected MemberVO mustGetLoginUser() throws AjaxException {
        MemberVO mvo = LoginAjaxAction.getLoginUserFrom(currentRequest);
        if (mvo == null) {
            throw new AjaxException(
                    SC_UNAUTHORIZED,
                    "로그인이 필요합니다",
                    LoginAjaxAction.getLoginUrl(UrlUtil.getUrlPath(currentRequest)));
        }
        return mvo;
    }
}
