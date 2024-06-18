package com.team4.museum.controller.action.member;

import com.team4.museum.controller.action.Action;
import com.team4.museum.util.UrlUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContractAcion implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 이미 로그인 되어있는 경우
        if (LoginAjaxAction.isLoginedFrom(request)) {
            response.sendRedirect("museum.do?command=index");
            return;
        }

        // 파라미터의 'returnUrl'을 인코딩하여 속성으로 함께 전달
        // JSP에서 action 경로 뒤에 붙여야 하기 때문에 인코딩이 필요함
        request.setAttribute("returnUrl", UrlUtil.encode(request.getParameter("returnUrl")));
        request.getRequestDispatcher("/WEB-INF/views/member/contract.jsp").forward(request, response);
    }

}
