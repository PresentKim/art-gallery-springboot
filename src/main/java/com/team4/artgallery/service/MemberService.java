package com.team4.artgallery.service;

import com.team4.artgallery.dao.IMemberDao;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.UrlUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MemberService {

    @Delegate
    private final IMemberDao memberDao;

    /**
     * 주어진 ID에 해당하는 회원이 존재하는지 확인한다.
     *
     * @param id 확인할 회원 ID
     * @return 회원이 존재하면 true, 그렇지 않으면 false
     */
    public boolean isMember(String id) {
        return memberDao.findMember(id) != null;
    }

    /**
     * 세션에 저장된 회원 정보를 반환한다.
     *
     * @param session 세션 객체
     * @return 세션에 저장된 회원 정보, 없으면 null
     */
    public MemberDto getLoginMember(HttpSession session) {
        return (MemberDto) session.getAttribute("account");
    }

    /**
     * 세션에 회원 정보를 저장한다.
     *
     * @param session   세션 객체
     * @param memberDto 저장할 회원 정보
     */
    public void setLoginMember(HttpSession session, MemberDto memberDto) {
        session.setAttribute("account", memberDto);
    }

    /**
     * 세션에 저장된 회원 정보가 있는지 확인한다.
     *
     * @param session 세션 객체
     * @return 로그인 상태이면 true, 그렇지 않으면 false
     */
    public boolean isLogin(HttpSession session) {
        return getLoginMember(session) != null;
    }

    /**
     * 주어진 ID와 비밀번호로 로그인을 시도한다.
     *
     * @param session 세션 객체
     * @param id      로그인 시도할 ID
     * @param pwd     로그인 시도할 비밀번호
     * @return 로그인 성공 시 true, 그렇지 않으면 false
     */
    public boolean login(HttpSession session, String id, String pwd) {
        MemberDto memberDto = memberDao.findMember(id);
        if (memberDto == null || !memberDto.getPwd().equals(pwd)) {
            return false;
        }

        setLoginMember(session, memberDto);
        return true;
    }

    /**
     * 로그아웃을 시도한다.
     *
     * @param session 세션 객체
     * @return 로그아웃 성공 시 true, 그렇지 않으면 false
     */
    public boolean logout(HttpSession session) {
        if (!isLogin(session)) {
            return false;
        }

        session.removeAttribute("account");
        return true;
    }

    /**
     * 주어진 페이지로 되돌아가는 로그인 페이지로 리다이렉트하기 위한 URL 을 반환한다.
     *
     * @param returnUrl 되돌아갈 페이지 URL
     */
    public String redirectToLogin(String returnUrl) {
        return "redirect:/member/login?returnUrl=" + UrlUtil.encode(returnUrl);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginForm {

        @NotBlank(message = "아이디는 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "아이디는 4자 이상 45자 이하로 입력해주세요.")
        private String id;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 4, max = 45, message = "비밀번호는 4자 이상 45자 이하로 입력해주세요.")
        private String pwd;

    }

}
