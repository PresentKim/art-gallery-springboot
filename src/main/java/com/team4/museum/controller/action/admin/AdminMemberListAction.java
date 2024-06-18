package com.team4.museum.controller.action.admin;

import com.team4.museum.controller.action.Action;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.Pagination;
import com.team4.museum.util.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminMemberListAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한이 없으면 404 페이지로 포워딩
        if (!Security.adminOr404Forward(request, response)) {
            return;
        }

        MemberDao mdao = MemberDao.getInstance();
        String searchWord = request.getParameter("searchWord");

        Pagination pagination = Pagination.with(request, mdao.getAllCount(), "command=adminMemberList");
        if (searchWord != null) {
            pagination.setItemCount(mdao.getSearchCount(searchWord));
            pagination.setUrlTemplate("museum.do?command=adminMemberList&page=%d&searchWord=" + searchWord);
            request.setAttribute("memberList", mdao.searchMemberList(pagination, searchWord));
            request.setAttribute("searchWord", searchWord);
        } else {
            request.setAttribute("memberList", mdao.getMemberList(pagination));
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/adminMemberList.jsp").forward(request, response);
    }

}
