package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.UnauthorizedException;
import com.team4.artgallery.dto.filter.QnaFilter;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/qna", produces = MediaType.TEXT_HTML_VALUE)
public class QnaViewController {

    private final QnaService qnaService;

    public QnaViewController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping
    public String root(
            @Valid
            @ModelAttribute("pagination")
            Pagination pagination,

            Model model
    ) {
        model.addAttribute("qnaList", qnaService.getInquiries(new QnaFilter(), pagination));
        return "qna/qnaList";
    }

    @GetMapping("{qseq}")
    public String view(
            @PathVariable(name = "qseq")
            Integer qseq,

            Model model
    ) throws NotFoundException, UnauthorizedException {
        model.addAttribute("qnaEntity", qnaService.getInquiry(qseq));
        return "qna/qnaView";
    }


    @GetMapping("write")
    public String write(
            @RequestParam(name = "qseq", required = false)
            Integer qseq,

            Model model
    ) throws NotFoundException, UnauthorizedException {
        if (qseq != null) {
            model.addAttribute("qnaEntity", qnaService.getInquiry(qseq));
        }
        return "qna/qnaWrite";
    }

}
