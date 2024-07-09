package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import com.team4.artgallery.controller.resolver.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.service.FavoriteService;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/member", produces = MediaType.TEXT_HTML_VALUE)
public class MemberViewController {

    private final MemberService memberService;
    private final FavoriteService favoriteService;

    public MemberViewController(MemberService memberService, FavoriteService favoriteService) {
        this.memberService = memberService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("login")
    public String login(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,

            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 으로 리다이렉트
        if (memberService.isLogin()) {
            return "redirect:" + returnUrl;
        }

        model.addAttribute("returnUrl", returnUrl);
        return "member/loginForm";
    }

    @GetMapping("contract")
    public String contract(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,

            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin()) {
            return "redirect:" + returnUrl;
        }

        model.addAttribute("returnUrl", returnUrl);
        return "member/contract";
    }

    @GetMapping("join")
    public String join(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,

            Model model
    ) {
        // 이미 로그인 상태라면 returnUrl 로 리다이렉트
        if (memberService.isLogin()) {
            return "redirect:" + returnUrl;
        }

        model.addAttribute("returnUrl", returnUrl);
        return "member/joinForm";
    }

    @CheckLogin("/member/mypage")
    @GetMapping("mypage")
    public String mypage() {
        return "member/mypage/index";
    }

    @CheckLogin("/member/mypage/edit")
    @GetMapping("mypage/edit")
    public String mypageEdit() {
        return "member/mypage/mypageEditForm";
    }

    @CheckLogin("/member/withdraw")
    @GetMapping("withdraw")
    public String withdraw(
            @RequestParam(name = "returnUrl", defaultValue = "/")
            String returnUrl,

            Model model
    ) {
        model.addAttribute("returnUrl", returnUrl);
        return "member/mypage/withdrawForm";
    }

    @CheckLogin("/member/mypage/favorite")
    @GetMapping("mypage/favorite")
    public String favorite(
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            @LoginMember
            MemberDto loginMember,
            Model model
    ) {
        model.addAttribute("artworkList", favoriteService.getFavorites(loginMember.getId(), pagination));
        return "member/mypage/mypageFavoriteList";
    }

}
