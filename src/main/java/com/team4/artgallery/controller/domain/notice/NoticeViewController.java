package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/notice", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class NoticeViewController {

    private final NoticeService noticeService;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute("filter") NoticeFilter filter,
            @Valid @ModelAttribute("pagination") Pagination pagination,
            Model model
    ) {
        // 소식지 목록을 가져올 때 카테고리에 따라 다른 페이지로 리다이렉트
        String category = filter.getCategory();
        if (NoticeCategory.MAGAZINE.isEquals(category)) {
            return "redirect:/notice/magazine";
        } else if (NoticeCategory.NEWSPAPER.isEquals(category)) {
            return "redirect:/notice/newspaper";
        }

        // 검색 조건에 따라 소식지 목록을 가져옵니다.
        pagination.setItemCount(noticeService.countNotices(filter))
                .setUrlTemplate("/notice?page=%d" + filter.getUrlParam());

        model.addAttribute("noticeList", noticeService.getNotices(filter, pagination));
        return "notice/noticeList";
    }

    @GetMapping({"/{nseq}", "/view/{nseq}"})
    public String view(@PathVariable(value = "nseq") Integer nseq, HttpSession session, Model model) {
        // 소식지 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        if (noticeDto == null) {
            return "util/404";
        }

        // 소식지를 읽은 것으로 처리
        noticeService.markAsRead(session, nseq);

        // 소식지 정보를 뷰에 전달
        model.addAttribute("noticeDto", noticeDto);
        return "notice/noticeView";
    }

    @CheckAdmin
    @GetMapping("/update")
    public String update(@RequestParam(value = "nseq") Integer nseq, Model model) {
        // 소식지 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        if (noticeDto == null) {
            return "util/404";
        }

        // 소식지 정보를 뷰에 전달
        model.addAttribute("noticeDto", noticeDto);
        return "notice/noticeForm";
    }

    @CheckAdmin
    @GetMapping("/write")
    public String write() {
        return "notice/noticeForm";
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
