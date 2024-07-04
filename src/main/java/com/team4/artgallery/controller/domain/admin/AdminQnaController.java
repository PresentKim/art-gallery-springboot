package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/qna")
@CheckAdmin
@RequiredArgsConstructor
public class AdminQnaController {

    private final QnaService qnaService;

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("filter")
            QnaFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        pagination.setItemCount(qnaService.countInquiries(filter))
                .setUrlTemplate("/admin/qna?page=%d" + filter.getUrlParam());

        model.addAttribute("qnaList", qnaService.getInquiries(filter, pagination));
        return "admin/adminQnaList";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(
            @Valid
            @NotEmpty(message = "문의글을 선택해주세요")
            @RequestParam(value = "qseqs", required = false) List<Integer> qseqs
    ) {
        qnaService.deleteInquiries(qseqs);
        return ok("문의글 정보를 제거했습니다", ":reload");
    }

}
