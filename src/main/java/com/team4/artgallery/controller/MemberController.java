package com.team4.artgallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "member/loginForm";
    }

    @GetMapping("/join")
    public String register() {
        return "member/joinForm";
    }

    @GetMapping("/contract")
    public String root() {
        return "member/contract";
    }

    @GetMapping("/withdraw")
    public String withdraw() {
        return "member/withdrawForm";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "member/mypage/mypage";
    }

    @GetMapping("/mypage/edit")
    public String edit() {
        return "member/mypage/mypageEditForm";
    }

    @GetMapping("/mypage/favorite")
    public String favorite() {
        return "member/mypage/mypageFavoriteList";
    }
}
