package com.team4.artgallery.controller.domain.qna;

import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.service.QnaService;
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
            @Valid @ModelAttribute("pagination") Pagination pagination,
            Model model
    ) {
        pagination.setItemCount(qnaService.countInquiries(null))
                .setUrlTemplate("/qna?page=%d");

        model.addAttribute("qnaList", qnaService.getInquiries(null, pagination));
        return "qna/qnaList";
    }

    @GetMapping({"/{qseq}", "/view/{qseq}"})
    public String view(@PathVariable(value = "qseq") Integer qseq, Model model, HttpSession session) {
        // 문의글 정보가 없는 경우 404 페이지로 이동
        QnaDto qnaDto = qnaService.getInquiry(qseq);
        if (qnaDto == null) {
            return "util/404";
        }

        // 접근 권한이 없는 경우 오류 메시지 출력
        if (!qnaService.authorizeForRestrict(session, qseq)) {
            model.addAttribute("message", "잘못된 접근입니다.");
            return "util/alert";
        }

        // 문의글 조회 페이지로 이동
        model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        return "qna/qnaView";
    }

    @GetMapping("/write")
    public String write() {
        return "qna/qnaForm";
    }

    @GetMapping("/update")
    public String update(@RequestParam(value = "qseq") Integer qseq, Model model, HttpSession session) {
        // 문의글 정보가 없는 경우 404 페이지로 이동
        QnaDto qnaDto = qnaService.getInquiry(qseq);
        if (qnaDto == null) {
            return "util/404";
        }

        // 접근 권한이 없는 경우 오류 메시지 출력
        if (!qnaService.authorizeForPersonal(session, qseq)) {
            model.addAttribute("message", "잘못된 접근입니다.");
            return "util/alert";
        }

        // 문의글 수정 페이지로 이동
        model.addAttribute("qnaDto", qnaService.getInquiry(qseq));
        return "qna/qnaForm";
    }

}
