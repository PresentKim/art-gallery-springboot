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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam(value = "page", defaultValue = "1") int page,
            HttpSession session,
            Model model
    ) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            System.out.println("관리자가 아닙니다");
            return "util/404";
        }

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 예술품 목록을 가져옵니다.
        Pagination.Pair<QnaDto> pair = qnaService.getOrSearchInquiries(page, filter, "admin/qna");
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("qnaList", pair.list());
        return "admin/adminQnaList";
    }

}
