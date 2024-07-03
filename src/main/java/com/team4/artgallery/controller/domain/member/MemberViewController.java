package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.FavoriteDto;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/member", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

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
            HttpSession session,
            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin(session)) {
            return "redirect:" + returnUrl;
        }

        // 회원가입 페이지로 이동 (returnUrl 전달)
        model.addAttribute("returnUrl", returnUrl);
        return "member/joinForm";
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

}
