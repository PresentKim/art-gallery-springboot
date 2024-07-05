package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.ResponseDto;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.QnaService;
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
@RequestMapping("/admin/qna")
@CheckAdmin
@RequiredArgsConstructor
public class AdminQnaController {

    private final QnaService qnaService;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("filter")
            QnaFilter filter,
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) throws SqlException {
        model.addAttribute("qnaList", qnaService.getInquiriesPair(filter, pagination).list());
        return "admin/adminQnaList";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseDto delete(
            @Valid
            @NotEmpty(message = "문의글을 선택해주세요")
            @RequestParam(name = "qseq", required = false) List<Integer> qseq
    ) throws SqlException {
        qnaService.deleteInquiry(qseq);
        return new ResponseDto("문의글 정보를 제거했습니다", ":reload");
    }

}
