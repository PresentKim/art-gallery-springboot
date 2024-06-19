package com.team4.museum.controller;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.UrlUtil;
import com.team4.museum.controller.action.Action;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MuseumServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public MuseumServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 요청과 응답의 인코딩을 UTF-8로 설정
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 로그인 정보를 'userId'에서 가져오기
        MemberDto memberDto = (MemberDto) request.getSession().getAttribute("account");

        // 로그인 정보가 존재하는 경우
        if (memberDto != null) {
            String userId = memberDto.getId();

            // 로그인 정보가 관리자인 경우
            if (memberDto.isAdmin()) {
                System.out.print("[ADMIN:" + userId + "] ");
            } else {
                System.out.print("[MEMBER:" + userId + "] ");
            }
        } else {
            System.out.print("[GEUST] ");
        }

        // URL Path 정보를 'urlPath'에 저장
        String urlPath = UrlUtil.getUrlPath(request);
        request.setAttribute("urlPath", UrlUtil.encode(urlPath));

        // 'command' 파라미터로 액션을 탐색
        String command = request.getParameter("command");

        System.out.print("Request : " + urlPath + " -> ");
        Action ac = ActionFactory.getInstance().getAction(command);
        if (ac == null) {
            // 요청된 액션이 존재하지 않는 경우 404 페이지로 포워딩
            System.out.println("Action not found : " + command);
            request.getRequestDispatcher("/WEB-INF/views/util/404.jsp").forward(request, response);
            return;
        }
        System.out.println(ac.getClass().getSimpleName());

        // 요청된 액션을 실행
        ac.execute(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
