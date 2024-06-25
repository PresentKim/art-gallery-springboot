package com.team4.artgallery.controller;

import com.team4.artgallery.annotation.CheckLogin;
import com.team4.artgallery.annotation.LoginMember;
import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session,
            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 으로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        // 로그인 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/loginForm";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            @Validated(MemberDto.OnLogin.class) @ModelAttribute MemberDto loginForm,
            HttpSession session
    ) {
        // 이미 로그인 상태라면 에러 결과와 함께 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return badRequest("이미 로그인 상태입니다.", returnUrl);
        }

        // 로그인 실패 시 에러 결과 반환
        if (!memberService.login(session, loginForm.getId(), loginForm.getPwd())) {
            return badRequest("ID 혹은 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 성공 결과와 돌아갈 URL 반환 (로그인 성공은 메시지 없이 returnUrl 로 리다이렉트)
        return ok("", returnUrl);
    }

    @CheckLogin()
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session
    ) {
        // 로그아웃 실패 시 에러 결과 반환
        if (!memberService.logout(session)) {
            return internalServerError("로그아웃에 실패하였습니다");
        }

        // 로그아웃 성공 시 성공 결과와 돌아갈 URL 반환
        return ok("로그아웃에 성공하였습니다", returnUrl);
    }

    @GetMapping("/contract")
    public String contract(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            HttpSession session,
            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        // 이용약관 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/contract";
    }

    @GetMapping("/join")
    public String join(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            Model model,
            HttpSession session
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        model.addAttribute("returnUrl", returnUrl);
        return "member/joinForm";
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            @Validated(MemberDto.OnForm.class) @ModelAttribute MemberDto memberDto
    ) {
        // 이미 사용중인 아이디라면 에러 결과 반환
        if (memberService.isMember(memberDto.getId())) {
            return badRequest("이미 사용중인 아이디입니다.");
        }

        // 회원가입 실패 시 에러 결과 반환
        if (memberService.createMember(memberDto) != 1) {
            return badRequest("회원가입에 실패하였습니다.");
        }

        // 회원가입 성공 시 성공 결과와 돌아갈 URL 반환 (로그인 페이지로 이동)
        return ok("회원가입에 성공하였습니다.", "/member/login?returnUrl=" + URLEncoder.encode(returnUrl, StandardCharsets.UTF_8));
    }

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(@Valid @NotNull @RequestParam(name = "id") String id) {
        // 이미 사용중인 아이디라면 에러 결과 반환
        if (memberService.isMember(id)) {
            return badRequest("이미 사용중인 아이디입니다.");
        }

        // 사용 가능한 아이디라면 성공 결과 반환
        return ok("사용 가능한 아이디입니다.");
    }

    @CheckLogin("/member/mypage")
    @GetMapping("/mypage")
    public String mypage() {
        // 마이페이지로 이동
        return "member/mypage/index";
    }

    @CheckLogin("/member/mypage/edit")
    @GetMapping("/mypage/edit")
    public String mypageEdit() {
        // 회원 정보 수정 페이지로 이동
        return "member/mypage/mypageEditForm";
    }

    @CheckLogin()
    @PostMapping("/mypage/edit")
    public ResponseEntity<?> edit(
            @Validated(MemberDto.OnForm.class) @ModelAttribute MemberDto memberDto,
            @LoginMember MemberDto loginMember,
            HttpSession session
    ) {
        // 로그인 회원의 ID 를 수정할 수 없도록 설정
        memberDto.setId(loginMember.getId());

        // 회원 정보 수정 실패 시 에러 결과 반환
        if (memberService.updateMember(memberDto) != 1) {
            return badRequest("회원정보 수정에 실패하였습니다.");
        }

        // 회원 정보 수정 성공 시 성공 결과 반환
        memberService.setLoginMember(session, memberDto);
        return ok("회원정보 수정에 성공하였습니다.", "/member/mypage");
    }

    @CheckLogin("/member/withdraw")
    @GetMapping("/withdraw")
    public String withdraw(
            @RequestParam(value = "returnUrl", defaultValue = "/") String returnUrl,
            Model model
    ) {
        // 회원 탈퇴 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/mypage/withdrawForm";
    }

    @CheckLogin()
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @Valid @NotNull @RequestParam(name = "pwd") String pwd,
            @LoginMember MemberDto loginMember,
            HttpSession session
    ) {
        // 비밀번호가 일치하지 않다면 에러 결과 반환
        if (!loginMember.getPwd().equals(pwd)) {
            return badRequest("비밀번호가 일치하지 않습니다.");
        }

        // 회원 탈퇴 실패 시 에러 결과 반환
        if (memberService.deleteMember(loginMember.getId()) != 1) {
            return badRequest("회원 탈퇴에 실패하였습니다.");
        }

        // 로그아웃 실패 시 에러 결과 반환
        if (!memberService.logout(session)) {
            return badRequest("로그아웃에 실패하였습니다.");
        }

        // 회원 탈퇴 성공 시 성공 결과와 돌아갈 URL 반환
        return ok("회원 탈퇴에 성공하였습니다.", "/");
    }

    @CheckLogin("/member/mypage/favorite")
    @GetMapping("/mypage/favorite")
    public String favorite(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @LoginMember MemberDto loginMember,
            Model model
    ) {
        // 관심 예술품 페이지로 이동
        Pagination.Pair<FavoriteDto> pair = favoriteService.getFavorites(loginMember.getId(), page);
        model.addAttribute("artworkList", pair.list());
        model.addAttribute("pagination", pair.pagination());
        return "member/mypage/mypageFavoriteList";
    }

    @CheckLogin("/artwork/${aseq}")
    @PostMapping("/mypage/favorite")
    public ResponseEntity<?> favorite(@RequestParam(value = "aseq") Integer aseq, @LoginMember MemberDto loginMember) {
        try {
            // 관심 예술품 토글 성공 시 성공 결과 반환
            boolean result = favoriteService.toggleFavorite(loginMember.getId(), aseq);
            return ok("관심 예술품 목록에 " + (result ? "추가" : "삭제") + "되었습니다.");
        } catch (Exception e) {
            // 관심 예술품 토글 실패 시 에러 결과 반환
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            return badRequest("관심 예술품 처리에 실패하였습니다.");
        }
    }

}
