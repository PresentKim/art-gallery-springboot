package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.exception.BadRequestException;
import com.team4.artgallery.controller.exception.ConflictException;
import com.team4.artgallery.controller.exception.InternalServerErrorException;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.ResponseBody;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Assert;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

    @PostMapping("/login")
    public Object login(
            @RequestParam(value = "returnUrl", defaultValue = "/")
            String returnUrl,
            @Validated(MemberDto.OnLogin.class)
            MemberDto loginForm,

            HttpSession session
    ) throws Exception {
        Assert.isFalse(memberService.isLogin(session), "이미 로그인 상태입니다.", BadRequestException::new);

        Assert.trueOrUnauthorized(
                memberService.login(session, loginForm.getId(), loginForm.getPwd()),
                "ID 혹은 비밀번호가 일치하지 않습니다."
        );

        // 로그인 성공은 메시지 없이 returnUrl 로 리다이렉트
        return new URI(returnUrl);
    }

    @CheckLogin
    @PostMapping("/logout")
    public ResponseBody logout(
            @RequestParam(value = "returnUrl", defaultValue = "/")
            String returnUrl,

            HttpSession session
    ) {
        memberService.logout(session);
        return new ResponseBody("로그아웃에 성공하였습니다", returnUrl);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseBody join(
            @RequestParam(value = "returnUrl", defaultValue = "/")
            String returnUrl,
            @Validated(MemberDto.OnJoin.class)
            MemberDto memberDto
    ) {
        Assert.isFalse(memberService.isMember(memberDto.getId()), "이미 사용중인 아이디입니다.", ConflictException::new);

        memberService.createMember(memberDto);

        return new ResponseBody("회원가입에 성공하였습니다.", memberService.getRedirectToLogin(returnUrl));
    }

    @PostMapping("/idCheck")
    public Object idCheck(
            @Valid
            @NotBlank(message = "아이디를 입력해주세요")
            @RequestParam(name = "id")
            String id
    ) {
        Assert.isFalse(memberService.isMember(id), "이미 사용중인 아이디입니다.", ConflictException::new);

        return "사용 가능한 아이디입니다.";
    }

    @CheckLogin
    @PostMapping("/mypage/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseBody edit(
            @Validated(MemberDto.OnUpdate.class)
            MemberDto memberDto,

            @LoginMember
            MemberDto loginMember,
            HttpSession session
    ) {
        // 로그인 회원의 ID 를 수정할 수 없도록 설정
        memberDto.setId(loginMember.getId());

        // 비밀번호가 입력되지 않았다면 기존 비밀번호로 설정
        if (memberDto.getPwd() == null || memberDto.getPwd().isEmpty()) {
            memberDto.setPwd(loginMember.getPwd());
        }

        memberService.updateMember(memberDto);
        memberService.setLoginMember(session, memberDto);
        return new ResponseBody("회원정보 수정에 성공하였습니다.", "/member/mypage");
    }

    @CheckLogin
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody withdraw(
            @Valid
            @NotBlank(message = "비밀번호를 입력해주세요")
            @RequestParam(name = "pwd")
            String pwd,

            @LoginMember
            MemberDto loginMember,
            HttpSession session
    ) {
        Assert.trueOrUnauthorized(loginMember.getPwd().equals(pwd), "ID 혹은 비밀번호가 일치하지 않습니다.");
        Assert.isTrue(memberService.logout(session), "로그아웃에 실패하였습니다.", InternalServerErrorException::new);

        memberService.deleteMember(loginMember.getId());

        return new ResponseBody("회원 탈퇴에 성공하였습니다.", "/");
    }

    @CheckLogin("/artwork/${aseq}")
    @PostMapping("/mypage/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    public Object favorite(
            @RequestParam(value = "aseq")
            Integer aseq,

            @LoginMember
            MemberDto loginMember
    ) {
        boolean result = favoriteService.toggleFavorite(loginMember.getId(), aseq);
        return "관심 예술품 목록에 " + (result ? "추가" : "삭제") + "되었습니다.";
    }

}
