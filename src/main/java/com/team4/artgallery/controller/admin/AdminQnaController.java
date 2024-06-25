package com.team4.artgallery.controller.admin;

import com.team4.artgallery.annotation.CheckAdmin;
import com.team4.artgallery.dao.IQnaDao;
import com.team4.artgallery.dto.QnaDto;
import com.team4.artgallery.service.QnaService;
import com.team4.artgallery.util.Pagination;
import com.team4.artgallery.util.ajax.ResponseHelper;
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
@CheckAdmin
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminQnaController {

    private final QnaService qnaService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute IQnaDao.Filter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 문의글 목록을 가져옵니다.
        Pagination.Pair<QnaDto> pair = qnaService.getOrSearchInquiries(page, filter, "admin/qna");
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("qnaList", pair.list());
        return "admin/adminQnaList";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "qseqs", required = false) List<Integer> qseqs) {
        // qseqs 값이 없는 경우 요청 거부 결과 반환
        if (qseqs == null || qseqs.isEmpty()) {
            return badRequest("문의글을 선택해주세요");
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
