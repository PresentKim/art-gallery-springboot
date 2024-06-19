package com.team4.museum.controller.action.member;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.UrlUtil;
import com.team4.museum.controller.action.AjaxAction;
import com.team4.museum.dao.MemberDao;
import com.team4.museum.util.ajax.AjaxException;
import com.team4.museum.util.ajax.AjaxResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginAjaxAction extends AjaxAction {

    protected AjaxResult handleAjaxRequest(HttpServletRequest request, HttpServletResponse response)
            throws AjaxException {
        // 로그인에 필요한 정보를 받아옴 (id, pwd)
        // mustGetString() 호출 시 요청한 파라미터가 없으면 AjaxException을 던짐
        String id = mustGetString("id");
        String pwd = mustGetString("pwd");

        // 입력된 'id'에 해당하는 사용자 계정이 없는 경우
        MemberDto memberDto = MemberDao.getInstance().getMember(id);
        if (memberDto == null) {
            return badRequest("ID 혹은 비밀번호가 일치하지 않습니다"); // 보안을 위해 ID 존재 여부를 알리지 않음
        }

        // 입력된 'pwd'가 사용자 계정의 비밀번호와 일치하지 않는 경우
        if (!memberDto.getPwd().equals(pwd)) {
            return badRequest("ID 혹은 비밀번호가 일치하지 않습니다");
        }

        // 로그인 성공 시 세션에 로그인 정보를 저장
        HttpSession session = request.getSession();
        session.setAttribute("account", memberDto);

        // 돌아갈 페이지 정보와 함께 성공 메시지를 반환
        return ok("로그인에 성공하였습니다", getReturnUrl());
    }

    /**
     * 세션에서 관리자 여부를 확인한다.
     *
     * @param request
     * @return 관리자면 true, 아니면 false
     */
    public static boolean isAdmin(HttpServletRequest request) {
        return request.getAttribute("account") != null && ((MemberDto) request.getAttribute("account")).isAdmin();
    }

    /**
     * 세션에서 로그인한 사용자의 정보를 가져온다.
     *
     * @param request
     * @return 로그인한 사용자의 정보 (MemberDto), 로그인이 안되어 있으면 null
     * @throws IOException
     */
    public static MemberDto getLoginUserFrom(HttpServletRequest request) {
        return (MemberDto) request.getSession().getAttribute("account");
    }

    /**
     * 로그인이 필요한 페이지에서 로그인 여부를 확인한다.
     *
     * @param request
     * @param response
     * @return 로그인이 되어 있으면 true, 아니면 false
     * @throws IOException
     */
    public static boolean isLoginedFrom(HttpServletRequest request) throws IOException {
        return getLoginUserFrom(request) != null;
    }

    /**
     * 로그인이 필요한 페이지에서 세션에서 로그인한 사용자의 정보를 가져온다. 로그인이 안되어 있으면 자동으로 로그인 페이지로 이동한다.
     *
     * @param request
     * @param response
     * @return 로그인한 사용자의 정보 (MemberDto), 로그인이 안되어 있으면 null
     * @throws IOException
     */
    public static MemberDto getLoginUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return getLoginUser(request, response, UrlUtil.getUrlPath(request));
    }

    /**
     * 로그인이 필요한 페이지에서 세션에서 로그인한 사용자의 정보를 가져온다. 로그인이 안되어 있으면 자동으로 로그인 페이지로 이동한다.
     *
     * @param request
     * @param response
     * @param returnUrl 로그인 후 돌아갈 페이지
     * @return 로그인한 사용자의 정보 (MemberDto), 로그인이 안되어 있으면 null
     * @throws IOException
     */
    public static MemberDto getLoginUser(HttpServletRequest request, HttpServletResponse response, String returnUrl)
            throws IOException {

        MemberDto memberDto = getLoginUserFrom(request);
        if (memberDto == null) {
            response.sendRedirect(getLoginUrl(returnUrl));
            return null;
        }

        return memberDto;
    }

    /**
     * 로그인이 필요한 페이지에서 로그인 여부를 확인한다. 로그인이 안되어 있으면 false 를 반환한다.
     *
     * @param request
     * @param response
     * @return 로그인이 되어 있으면 true, 아니면 false
     * @throws IOException
     */
    public static boolean isLogined(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return getLoginUser(request, response) != null;
    }

    /**
     * 로그인이 필요한 페이지에서 로그인 여부를 확인한다. 로그인이 안되어 있으면 false 를 반환한다.
     *
     * @param request
     * @param response
     * @param returnUrl 로그인 후 돌아갈 페이지
     * @return 로그인이 되어 있으면 true, 아니면 false
     * @throws IOException
     */
    public static boolean isLogined(HttpServletRequest request, HttpServletResponse response, String returnUrl)
            throws IOException {
        return getLoginUser(request, response, returnUrl) != null;
    }

    /**
     * 로그인 페이지 URL을 생성한다.
     *
     * @param returnUrl 로그인 후 돌아갈 페이지
     * @return 로그인 페이지 URL
     */
    public static String getLoginUrl(String returnUrl) {
        return "museum.do?command=loginForm&returnUrl=" + UrlUtil.encode(returnUrl);
    }

}
