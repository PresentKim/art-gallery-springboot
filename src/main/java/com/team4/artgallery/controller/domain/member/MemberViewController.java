package com.team4.artgallery.controller.domain.member;

import com.team4.artgallery.aspect.annotation.CheckLogin;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/member", produces = MediaType.TEXT_HTML_VALUE)
public class MemberViewController {

    @GetMapping("login")
    public String login() {
        return "member/login";
    }

    @GetMapping("contract")
    public String contract() {
        return "member/contract";
    }

    @GetMapping("join")
    public String join() {
        return "member/join";
    }

    @CheckLogin("/member/withdraw")
    @GetMapping("withdraw")
    public String withdraw() {
        return "member/withdraw";
    }

    @CheckLogin("/member/mypage")
    @GetMapping("mypage")
    public String mypage() {
        return "member/mypage";
    }

    @CheckLogin("/member/update")
    @GetMapping("update")
    public String update() {
        return "member/update";
    }

}
