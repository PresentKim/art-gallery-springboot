package com.team4.artgallery.controller.domain.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("qnaList", qnaService.getInquiries(filter, pagination));
        return "admin/adminQnaList";
    }

}
