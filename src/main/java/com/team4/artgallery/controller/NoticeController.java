package com.team4.artgallery.controller;

import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ) {
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(noticeService.countNotices(category))
                .setUrlTemplate("/notice?page=%d" + (category == null ? "" : "&category=" + category));

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 예술품 목록을 가져옵니다.
        model.addAttribute("category", category);
        model.addAttribute("pagination", pagination);
        model.addAttribute("noticeList", noticeService.getNotices(category, pagination));
        return "notice/noticeList";
    }

    @GetMapping({"/{nseq}", "/view/{nseq}"})
    public String view(@PathVariable(value = "nseq") Integer nseq, Model model) {
        // 소식지 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        if (noticeDto == null) {
            return "util/404";
        }

        // 소식지 정보를 뷰에 전달
        model.addAttribute("noticeDto", noticeDto);
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
