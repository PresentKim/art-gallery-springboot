package com.team4.artgallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/")
    public String root() {
        return "notice/noticeList";
    }

    @GetMapping("/view")
    public String view() {
        return "notice/noticeView";
    }

    @GetMapping("/update")
    public String update() {
        return "notice/noticeUpdateForm";
    }

    @GetMapping("/write")
    public String write() {
        return "notice/noticeWriteForm";
    }

    @GetMapping("/delete")
    public String delete() {
        return "notice/noticeDeleteOk";
    }

    @GetMapping("/magazine")
    public String magazine() {
        return "notice/noticeMagazine";
    }

    @GetMapping("/newspaper")
    public String newspaper() {
        return "notice/noticeNewspaper";
    }

}
