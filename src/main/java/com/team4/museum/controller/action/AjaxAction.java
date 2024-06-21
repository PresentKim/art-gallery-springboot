package com.team4.museum.controller.action;

import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;

abstract public class AjaxAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AjaxResult result;

        try {
            // AJAX 요청을 처리
            result = handleAjaxRequest(request, response);
        } catch (AjaxException e) {
            // 확인된 AJAX 예외를 처리
            result = new AjaxResult(e.getStatusCode(), e.getMessage(), e.getUrl());
        } catch (Exception e) {
            // 확인되지 않은 예외를 처리
            e.printStackTrace();
            result = internalServerError();
        }

        // AJAX 결과를 응답
        response.setStatus(result.code);
        response.setContentType("application/json");
        response.getWriter().write(result.toJson());
    }

    /**
     * AJAX 요청을 처리하는 핸들러 메소드
     *
     * @param request  현재 요청
     * @param response 현재 응답
     * @return AJAX 결과
     */
    abstract protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AjaxException;

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

}
