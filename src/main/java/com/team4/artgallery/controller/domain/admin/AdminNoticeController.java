package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/notice")
@CheckAdmin
@RequiredArgsConstructor
public class AdminNoticeController {

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
        model.addAttribute("noticeList", noticeService.getNotices(filter, pagination));
        return "admin/adminNoticeList";
    }

}
