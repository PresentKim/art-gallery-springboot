package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
@CheckAdmin
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("filter")
            KeywordFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        model.addAttribute("memberList", memberService.getMembers(
                filter,
                pagination
                        .setUrlTemplateFromFilter(filter)
                        .setItemCount(memberService.countMembers(filter))
        ));
        return "admin/adminMemberList";
    }

}
