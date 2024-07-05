package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/qna", produces = MediaType.TEXT_HTML_VALUE)
@RequiredArgsConstructor
public class QnaViewController {

    private final QnaService qnaService;

    @GetMapping({"", "/"})
    public String list(
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        model.addAttribute("qnaList", qnaService.getInquiriesPair(new QnaFilter(), pagination).list());
        return "qna/qnaList";
    }

    @GetMapping("/{qseq}")
    public String view(
            @PathVariable(name = "qseq")
            Integer qseq,

            Model model
    ) throws NotFoundException, UnauthorizedException {
        model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        return "qna/qnaView";
    }


    @GetMapping("/write")
    public String write(
            @RequestParam(name = "qseq", required = false)
            Integer qseq,

            Model model
    ) throws NotFoundException, UnauthorizedException {
        if (qseq != null) {
            model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        }
        return "qna/qnaWrite";
    }

}
