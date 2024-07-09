package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.*;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Assert;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberRestController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

    public MemberRestController(MemberService memberService, FavoriteService favoriteService) {
        this.memberService = memberService;
        this.favoriteService = favoriteService;
    }

    @PostMapping("/login")
    public Object login(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,
            @Validated(MemberDto.OnLogin.class)
            MemberDto loginForm
    ) throws BadRequestException, UnauthorizedException, URISyntaxException {
        memberService.login(loginForm.getId(), loginForm.getPwd());
        return new URI(returnUrl);
    }

    @CheckLogin
    @PostMapping("/logout")
    public ResponseDto logout(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl
    ) throws UnauthorizedException {
        memberService.logout();
        return new ResponseDto("로그아웃에 성공하였습니다", returnUrl);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto join(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,
            @Validated(MemberDto.OnJoin.class)
            MemberDto memberDto
    ) throws ConflictException, SqlException {
        Assert.isFalse(memberService.isMember(memberDto.getId()), "이미 사용중인 아이디입니다.", ConflictException::new);

        memberService.createMember(memberDto);

        return new ResponseDto("회원가입에 성공하였습니다.", memberService.getRedirectToLogin(returnUrl));
    }

    @PostMapping("/idCheck")
    public Object idCheck(
            @Valid
            @NotBlank(message = "아이디를 입력해주세요")
            @RequestParam(name = "id")
            String id
    ) throws ConflictException {
        Assert.isFalse(memberService.isMember(id), "이미 사용중인 아이디입니다.", ConflictException::new);

        return "사용 가능한 아이디입니다.";
    }

    @CheckLogin
    @PostMapping("/mypage/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto edit(
            @Validated(MemberDto.OnUpdate.class)
            MemberDto memberDto,

            @LoginMember
            MemberDto loginMember
    ) throws SqlException {
        // 로그인 회원의 ID 를 수정할 수 없도록 설정
        memberDto.setId(loginMember.getId());

        // 비밀번호가 입력되지 않았다면 기존 비밀번호로 설정
        if (memberDto.getPwd() == null || memberDto.getPwd().isEmpty()) {
            memberDto.setPwd(loginMember.getPwd());
        }

        memberService.updateMember(memberDto);
        memberService.setLoginMember(memberDto);
        return new ResponseDto("회원정보 수정에 성공하였습니다.", "/member/mypage");
    }

    @CheckLogin
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto withdraw(
            @Valid
            @NotBlank(message = "비밀번호를 입력해주세요")
            @RequestParam(name = "pwd")
            String pwd,

            @LoginMember
            MemberDto loginMember
    ) throws BadRequestException, UnauthorizedException, SqlException {
        memberService.withdraw(pwd, loginMember);
        return new ResponseDto("회원 탈퇴에 성공하였습니다.", "/");
    }

    @CheckLogin("/artwork/${aseq}")
    @PostMapping("/mypage/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    public Object favorite(
            @RequestParam(name = "aseq")
            Integer aseq,

            @LoginMember
            MemberDto loginMember
    ) throws SqlException {
        boolean result = favoriteService.toggleFavorite(loginMember.getId(), aseq);
        return "관심 예술품 목록에 " + (result ? "추가" : "삭제") + "되었습니다.";
    }


    @CheckAdmin
    @PostMapping("/grant")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto grant(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.grantAdminMembers(memberIds);
        return new ResponseDto("관리자 권한을 부여했습니다", ":reload");
    }

    @CheckAdmin
    @PostMapping("/revoke")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto revoke(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.revokeAdminMembers(memberIds);
        return new ResponseDto("관리자 권한을 제거했습니다", ":reload");
    }

    @CheckAdmin
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.deleteMember(memberIds);
        return new ResponseDto("회원 정보를 제거했습니다", ":reload");
    }

}
