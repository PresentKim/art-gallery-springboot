package com.team4.museum.util;

import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AccountUtil {

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
     * 로그인 페이지 URL을 생성한다.
     *
     * @param returnUrl 로그인 후 돌아갈 페이지
     * @return 로그인 페이지 URL
     */
    public static String getLoginUrl(String returnUrl) {
        return "museum.do?command=loginForm&returnUrl=" + UrlUtil.encode(returnUrl);
    }

}
