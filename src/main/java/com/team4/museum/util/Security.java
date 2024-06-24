package com.team4.museum.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

final public class Security {

    /**
     * 관리자가 아닐 경우 404 페이지로 포워딩
     *
     * @return 관리자 여부
     */
    public static boolean adminOr404Forward(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        return trueOr404Forward(AccountUtil.isAdmin(request), request, response);
    }

    /**
     * 주어진 조건이 참이 아닐 경우 404 페이지로 포워딩
     *
     * @return 관리자 여부
     */
    public static boolean trueOr404Forward(
            boolean condition,
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        if (!condition) {
            request.getRequestDispatcher("/WEB-INF/views/util/404.jsp").forward(request, response);
        }
        return condition;
    }

}