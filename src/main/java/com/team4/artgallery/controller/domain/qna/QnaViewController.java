package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Assert;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
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
        pagination.setItemCount(qnaService.countInquiries(null)).setUrlTemplate("/qna?page=%d");
        model.addAttribute("qnaList", qnaService.getInquiries(null, pagination));
        return "qna/qnaList";
    }

    @GetMapping({"/{qseq}", "/view/{qseq}"})
    public String view(
            @PathVariable(value = "qseq")
            Integer qseq,

            Model model,
            HttpSession session
    ) {
        QnaDto qnaDto = qnaService.getInquiry(qseq);
        Assert.exists(qnaDto, "문의글 정보를 찾을 수 없습니다.");
        Assert.trueOrUnauthorized(qnaService.authorizeForRestrict(session, qseq), "잘못된 접근입니다.");

        model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        return "qna/qnaView";
    }

    @GetMapping("/write")
    public String write() {
        return "qna/qnaForm";
    }

    @GetMapping("/update")
    public String update(
            @RequestParam(value = "qseq")
            Integer qseq,

            Model model,
            HttpSession session
    ) {
        QnaDto qnaDto = qnaService.getInquiry(qseq);
        Assert.exists(qnaDto, "문의글 정보를 찾을 수 없습니다.");
        Assert.trueOrUnauthorized(qnaService.authorizeForPersonal(session, qseq), "잘못된 접근입니다.");

        model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        return "qna/qnaForm";
    }

}
