package com.team4.museum.controller.action.admin;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GrantAdminRightsAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        // request.getParameter("memberIds") 는 "Id1,Id2,Id3,..." String 값임
        String action = request.getParameter("action");
        String[] memberIds = request.getParameter("memberIds").split(",");

        for (String Id : memberIds) {
            MemberDao.getInstance().adminRightsAction(Id, action);
        }

        response.sendRedirect("museum.do?command=adminMemberList");
    }

}
