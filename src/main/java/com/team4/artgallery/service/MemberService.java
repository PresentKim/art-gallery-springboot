package com.team4.artgallery.service;

import com.team4.artgallery.dao.IMemberDao;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.helper.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Delegate
    private final IMemberDao memberDao;

    private final SessionService sessionService;

    /**
     * 주어진 ID에 해당하는 회원이 존재하는지 확인한다.
     *
     * @param id 확인할 회원 ID
     * @return 회원이 존재하면 true, 그렇지 않으면 false
     */
    public boolean isMember(String id) {
        return memberDao.getMember(id) != null;
    }

    /**
     * 세션에 저장된 회원 정보를 반환한다.
     *
     * @return 세션에 저장된 회원 정보, 없으면 null
     */
    public MemberDto getLoginMember() {
        return (MemberDto) sessionService.getSession().getAttribute("loginMember");
    }

    /**
     * 세션에 회원 정보를 저장한다.
     *
     * @param memberDto 저장할 회원 정보
     */
    public void setLoginMember(MemberDto memberDto) {
        sessionService.getSession().setAttribute("loginMember", memberDto);
    }

    /**
     * 세션에 저장된 회원 정보가 있는지 확인한다.
     *
     * @return 로그인 상태이면 true, 그렇지 않으면 false
     */
    public boolean isLogin() {
        return getLoginMember() != null;
    }

    /**
     * 세션에 저장된 회원 정보가 관리자인지 확인한다.
     *
     * @return 관리자이면 true, 그렇지 않으면 false
     */
    public boolean isAdmin() {
        MemberDto memberDto = getLoginMember();
        return memberDto != null && memberDto.isAdmin();
    }

    /**
     * 주어진 ID와 비밀번호로 로그인을 시도한다.
     *
     * @param id  로그인 시도할 ID
     * @param pwd 로그인 시도할 비밀번호
     * @return 로그인 성공 시 true, 그렇지 않으면 false
     */
    public boolean login(String id, String pwd) {
        MemberDto memberDto = memberDao.getMember(id);
        if (memberDto == null || !memberDto.getPwd().equals(pwd)) {
            return false;
        }

        setLoginMember(memberDto);
        return true;
    }

    /**
     * 로그아웃을 시도한다.
     *
     * @return 로그아웃 성공 시 true, 그렇지 않으면 false
     */
    public boolean logout() {
        if (!isLogin()) {
            return false;
        }

        sessionService.getSession().removeAttribute("loginMember");
        return true;
    }

    /**
     * 되돌아갈 페이지 주소가 포함된 로그인 페이지 URL을 반환한다.
     *
     * @param returnUrl 되돌아갈 페이지 주소가 포함된 로그인 페이지 URL
     */
    public String getRedirectToLogin(String returnUrl) {
        return "/member/login?returnUrl=" + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8);
    }

}
