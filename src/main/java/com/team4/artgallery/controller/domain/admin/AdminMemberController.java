package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/grant")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseDto grant(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.grantAdminMembers(memberIds);
        return new ResponseDto("관리자 권한을 부여했습니다", ":reload");
    }

    @PostMapping("/revoke")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseDto revoke(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.revokeAdminMembers(memberIds);
        return new ResponseDto("관리자 권한을 제거했습니다", ":reload");
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "회원을 선택해주세요")
            @RequestParam(name = "memberIds", required = false)
            List<String> memberIds
    ) throws NotFoundException {
        memberService.deleteMembers(memberIds);
        return new ResponseDto("회원 정보를 제거했습니다", ":reload");
    }

}
