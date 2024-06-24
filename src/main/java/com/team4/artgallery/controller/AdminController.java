package com.team4.artgallery.controller;

import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminController {

    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String root(HttpSession session) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            System.out.println("관리자가 아닙니다");
            return "util/404";
        }

        return "admin/adminMain";
    }

    @GetMapping("/member")
    public String member() {
        return "admin/adminMemberList";
    }

    @GetMapping("/artwork")
    public String artwork() {
        return "admin/adminArtworkList";
    }

    @GetMapping("/notice")
    public String notice() {
        return "admin/adminNoticeList";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "admin/adminGalleryList";
    }

    @GetMapping("/qna")
    public String qna() {
        return "admin/adminQnaList";
    }

    @GetMapping("/resetDB")
    public void resetDB() {
        // TODO: Implement this method
    }

}
