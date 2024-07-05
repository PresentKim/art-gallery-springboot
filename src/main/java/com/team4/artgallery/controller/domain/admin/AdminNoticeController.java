package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object edit(
            @Valid
            @NotEmpty(message = "소식지를 선택해주세요.")
            @Size(max = 1, message = "한 번에 하나의 소식지만 수정할 수 있습니다.")
            @RequestParam(name = "nseq") List<Integer> nseq
    ) throws URISyntaxException {
        return new URI("/notice/update/" + nseq.get(0));
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "소식지를 선택해주세요")
            @RequestParam(name = "nseq", required = false)
            List<Integer> nseq
    ) throws SqlException {
        noticeService.deleteNotice(nseq);
        return new ResponseDto("소식지 정보를 제거했습니다", ":reload");
    }

}
