package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.ConflictException;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.dto.member.MemberCreateDto;
import com.team4.artgallery.dto.member.MemberLoginDto;
import com.team4.artgallery.dto.member.MemberUpdateDto;
import com.team4.artgallery.entity.MemberEntity;
import com.team4.artgallery.repository.MemberRepository;
import com.team4.artgallery.service.helper.SessionProvider;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final SessionProvider sessionProvider;

    public MemberService(MemberRepository memberRepository, SessionProvider sessionProvider) {
        this.memberRepository = memberRepository;
        this.sessionProvider = sessionProvider;
    }

    /**
     * 세션에 저장된 회원 정보를 반환한다.
     *
     * @return 세션에 저장된 회원 정보, 없으면 null
     */
    public MemberEntity getLoginMember() {
        return (MemberEntity) sessionProvider.getSession().getAttribute("loginMember");
    }

    /**
     * 세션에 회원 정보를 저장한다.
     *
     * @param memberEntity 저장할 회원 정보
     */
    public void setLoginMember(MemberEntity memberEntity) {
        sessionProvider.getSession().setAttribute("loginMember", memberEntity);
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
        MemberEntity memberEntity = getLoginMember();
        return memberEntity != null && memberEntity.isAdmin();
    }

    /**
     * 주어진 ID에 해당하는 회원이 존재하는지 확인한다.
     *
     * @param id 확인할 회원 ID
     * @return 회원이 존재하면 true, 그렇지 않으면 false
     */
    public boolean isMember(String id) {
        return memberRepository.existsById(id);
    }

    /**
     * 주어진 ID와 비밀번호로 로그인을 시도한다.
     *
     * @param memberLoginDto 로그인 시도할 회원 정보
     * @throws ConflictException     이미 로그인 상태인 경우 예외 발생
     * @throws UnauthorizedException ID 혹은 비밀번호가 일치하지 않는 경우 예외 발생
     */
    public void login(MemberLoginDto memberLoginDto) throws BadRequestException, UnauthorizedException {
        Assert.isFalse(isLogin(), "이미 로그인 상태입니다.", ConflictException::new);

        MemberEntity memberEntity = memberRepository.findById(memberLoginDto.id()).orElse(null);
        Assert.trueOrUnauthorized(memberEntity != null && memberEntity.getPwd().equals(memberLoginDto.pwd()), "ID 혹은 비밀번호가 일치하지 않습니다.");

        setLoginMember(memberEntity);
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
     * @throws NotFoundException     회원 정보 삭제 중 오류가 발생한 경우 예외 발생
     */
    public void withdraw(String pwd, MemberEntity loginMember) throws BadRequestException, UnauthorizedException, NotFoundException {
        Assert.isTrue(pwd.equals(loginMember.getPwd()), "비밀번호가 일치하지 않습니다.", BadRequestException::new);

        memberRepository.deleteById(loginMember.getPwd());
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

    @Transactional
    public void createMember(MemberCreateDto memberCreateDto) throws com.team4.artgallery.controller.exception.SqlException {
        Assert.isFalse(isMember(memberCreateDto.id()), "이미 사용중인 아이디입니다.", ConflictException::new);

        memberRepository.save(memberCreateDto.toEntity());
    }

    public Page<MemberEntity> getMembers(KeywordFilter filter, Pagination pagination) {
        //noinspection unchecked
        Page<MemberEntity> result = memberRepository.findAll((Specification<MemberEntity>) filter.toSpec("name", "id"), pagination.toPageable());
        pagination.setItemCount((int) result.getTotalElements());
        return result;
    }

    /**
     * 회원 정보를 반환한다.
     *
     * @param id 회원 ID
     * @return 회원 정보
     * @throws NotFoundException 회원 정보를 찾을 수 없는 경우 예외 발생
     */
    public MemberEntity getMember(String id) throws NotFoundException {
        return memberRepository.findById(id).orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
    }

    public int countMembers(KeywordFilter filter) {
        //noinspection unchecked
        return (int) memberRepository.count((Specification<MemberEntity>) filter.toSpec("name", "id"));
    }

    @Transactional
    public void updateMember(String id, MemberUpdateDto memberUpdateDto) throws com.team4.artgallery.controller.exception.SqlException {
        MemberEntity memberEntity = getMember(id);
        memberEntity.setName(memberUpdateDto.name());
        memberEntity.setEmail(memberUpdateDto.email());
        memberEntity.setPhone(memberUpdateDto.phone());
        memberEntity.setAddress(memberUpdateDto.address());

        // 비밀번호는 선택적으로 업데이트
        String pwd = memberUpdateDto.pwd();
        if (pwd != null && !pwd.isEmpty()) {
            memberEntity.setPwd(pwd);
        }
    }

    @Transactional
    public void grantAdmin(String id) throws NotFoundException {
        MemberEntity memberEntity = getMember(id);
        memberEntity.setAdminyn('Y');
    }

    @Transactional
    public void revokeAdmin(String id) throws NotFoundException {
        MemberEntity memberEntity = getMember(id);
        memberEntity.setAdminyn('N');
    }

    @Transactional
    public void deleteMember(String id) throws NotFoundException {
        memberRepository.deleteById(id);
    }

}
