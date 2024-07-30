package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/notice", produces = MediaType.TEXT_HTML_VALUE)
public class NoticeViewController {

    private final NoticeService noticeService;

    public NoticeViewController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public String root(
            @Valid
            @ModelAttribute("filter")
            NoticeFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        String category = filter.getCategory();
        if (NoticeCategory.MAGAZINE.isEquals(category)) {
            return "redirect:/notice/magazine";
        } else if (NoticeCategory.NEWSPAPER.isEquals(category)) {
            return "redirect:/notice/newspaper";
        }

        model.addAttribute(
                "noticeList",
                noticeService.getNotices(filter, pagination).toList()
        );
        return "notice/noticeList";
    }

    @GetMapping("{nseq}")
    public String view(
            @PathVariable(name = "nseq")
            Integer nseq,

            Model model
    ) throws NotFoundException, SqlException {
        noticeService.increaseReadCountIfNew(nseq);
        model.addAttribute("noticeEntity", noticeService.getNotice(nseq));
        return "notice/noticeView";
    }

    @CheckAdmin
    @GetMapping("write")
    public String write(
            @RequestParam(name = "nseq", required = false)
            Integer nseq,

            Model model
    ) throws NotFoundException {
        if (nseq != null) {
            model.addAttribute("noticeEntity", noticeService.getNotice(nseq));
        }
        return "notice/noticeWrite";
    }

    @GetMapping("magazine")
    public String magazine() {
        return "notice/noticeMagazine";
    }

    @GetMapping("newspaper")
    public String newspaper() {
        return "notice/noticeNewspaper";
    }

}
