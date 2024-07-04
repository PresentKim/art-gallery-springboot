package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dao.IMemberDao;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
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

    private final SessionProvider sessionProvider;

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
        return (MemberDto) sessionProvider.getSession().getAttribute("loginMember");
    }

    /**
     * 세션에 회원 정보를 저장한다.
     *
     * @param memberDto 저장할 회원 정보
     */
    public void setLoginMember(MemberDto memberDto) {
        sessionProvider.getSession().setAttribute("loginMember", memberDto);
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
     * @throws BadRequestException   이미 로그인 상태인 경우 예외 발생
     * @throws UnauthorizedException ID 혹은 비밀번호가 일치하지 않는 경우 예외 발생
     */
    public void login(String id, String pwd) throws BadRequestException, UnauthorizedException {
        Assert.isFalse(isLogin(), "이미 로그인 상태입니다.", BadRequestException::new);

        MemberDto memberDto = memberDao.getMember(id);
        Assert.trueOrUnauthorized(memberDto != null && memberDto.getPwd().equals(pwd), "ID 혹은 비밀번호가 일치하지 않습니다.");

        setLoginMember(memberDto);
    }

    /**
     * 로그아웃을 시도한다.
     *
     * @throws UnauthorizedException 로그인 상태가 아닌 경우 예외 발생
     */
    public void logout() throws UnauthorizedException {
        Assert.trueOrUnauthorized(isLogin(), "로그인 상태가 아닙니다.");
        sessionProvider.getSession().removeAttribute("loginMember");
    }

    /**
     * 회원 탈퇴를 시도합니다.
     *
     * @param pwd         비밀번호
     * @param loginMember 로그인한 회원 정보
     * @throws BadRequestException   비밀번호가 일치하지 않는 경우 예외 발생
     * @throws UnauthorizedException 로그인 상태가 아닌 경우 예외 발생
     * @throws SqlException          회원 정보 삭제 중 오류가 발생한 경우 예외 발생
     */
    public void withdraw(String pwd, MemberDto loginMember) throws BadRequestException, UnauthorizedException, SqlException {
        Assert.isTrue(pwd.equals(loginMember.getPwd()), "ID 혹은 비밀번호가 일치하지 않습니다.", BadRequestException::new);

        memberDao.deleteMember(loginMember.getId());
        logout();
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
