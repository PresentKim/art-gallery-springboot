package com.team4.artgallery.controller.domain.notice;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.enums.NoticeCategory;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/notice", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class NoticeViewController {

    private final NoticeService noticeService;

    @GetMapping({"", "/"})
    public String list(
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

        pagination.setItemCount(noticeService.countNotices(filter)).setUrlTemplate("/notice?page=%d" + filter.getUrlParam());
        model.addAttribute("noticeList", noticeService.getNotices(filter, pagination));
        return "notice/noticeList";
    }

    @GetMapping("/{nseq}")
    public String view(
            @PathVariable(name = "nseq")
            Integer nseq,

            Model model
    ) {
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        Assert.exists(noticeDto, "소식지 정보를 찾을 수 없습니다.");

        noticeService.markAsRead(nseq);

        model.addAttribute("noticeDto", noticeDto);
        return "notice/noticeView";
    }

    @CheckAdmin
    @GetMapping("/update/{nseq}")
    public String update(
            @PathVariable(name = "nseq")
            Integer nseq,

            Model model
    ) {
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        Assert.exists(noticeDto, "소식지 정보를 찾을 수 없습니다.");

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
