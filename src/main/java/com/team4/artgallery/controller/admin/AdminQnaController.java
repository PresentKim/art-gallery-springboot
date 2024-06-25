package com.team4.artgallery.controller.admin;

import com.team4.artgallery.dao.IQnaDao;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.service.MemberService;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ajax.ResponseHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/qna")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminQnaController {

    private final MemberService memberService;

    private final QnaService qnaService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute IQnaDao.Filter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            HttpSession session,
            Model model
    ) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            System.out.println("관리자가 아닙니다");
            return "util/404";
        }

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 문의글 목록을 가져옵니다.
        Pagination.Pair<QnaDto> pair = qnaService.getOrSearchInquiries(page, filter, "admin/qna");
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("qnaList", pair.list());
        return "admin/adminQnaList";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "qseqs", required = false) List<Integer> qseqs,
            HttpSession session
    ) {
        // qseqs 값이 없는 경우 요청 거부 결과 반환
        if (qseqs == null || qseqs.isEmpty()) {
            return badRequest("문의글을 선택해주세요");
        }

        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 문의글 정보 제거에 실패한 경우 실패 결과 반환
        if (qnaService.deleteInquiries(qseqs) == 0) {
            return badRequest("문의글 정보 제거에 실패했습니다");
        }

        // 문의글 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("문의글 정보를 제거했습니다", ":reload");
    }

}
