package com.team4.artgallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    public String root() {
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
