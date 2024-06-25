package com.team4.artgallery.service;

import com.team4.artgallery.dao.IMemberDao;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.util.Pagination;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MemberService {

    @Delegate
    private final IMemberDao memberDao;

    /**
     * 회원 목록을 가져옵니다.
     *
     * @param page   페이지 번호
     * @param search 검색어  (검색어가 비어있으면 전체 회원 목록을 가져옵니다)
     * @return 회원 목록과 페이지네이션 정보
     */
    public Pagination.Pair<MemberDto> getOrSearchMembers(int page, String search) {
        // 검색어가 비어있을 경우 전체 회원 목록을 가져옵니다.
        if (search == null || search.isEmpty()) {
            Pagination pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(countMembers())
                    .setUrlTemplate("/admin/member?page=%d");

            return pagination.pair(getMembers(pagination));
        }

        // 검색 조건이 있을 경우 검색 결과를 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(countSearchMembers(search))
                .setUrlTemplate("/admin/member?page=%d&search=" + search);
        return pagination.pair(searchMembers(search, pagination));
    }

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
     * 세션에 저장된 회원 정보가 관리자인지 확인한다.
     *
     * @param session 세션 객체
     * @return 관리자이면 true, 그렇지 않으면 false
     */
    public boolean isAdmin(HttpSession session) {
        MemberDto memberDto = getLoginMember(session);
        return memberDto != null && memberDto.isAdmin();
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
        MemberDto memberDto = memberDao.getMember(id);
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
     * 되돌아갈 페이지 주소가 포함된 로그인 페이지 URL을 반환한다.
     *
     * @param returnUrl 되돌아갈 페이지 주소가 포함된 로그인 페이지 URL
     */
    public String getRedirectToLogin(String returnUrl) {
        return "/member/login?returnUrl=" + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8);
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
