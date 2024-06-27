package com.team4.artgallery.controller.admin;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.service.helper.ResponseService;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/notice")
@CheckAdmin
@RequiredArgsConstructor
public class AdminNoticeController {

    private final NoticeService noticeService;

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute NoticeFilter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 검색 조건에 따라 소식지 목록을 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(noticeService.countNotices(filter))
                .setUrlTemplate("/admin/notice?page=%d" + filter.toUrlParam());

        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pagination);
        model.addAttribute("noticeList", noticeService.getNotices(filter, pagination));
        return "admin/adminNoticeList";
    }

    @PostMapping("/update")
    public ResponseEntity<?> edit(@RequestParam(value = "nseqs", required = false) List<Integer> nseqs) {
        // nseqs 값이 없는 경우 요청 거부 결과 반환
        if (nseqs == null || nseqs.isEmpty()) {
            return badRequest("소식지를 선택해주세요");
        }

        // nseqs 값이 두개 이상인 경우 요청 거부 결과 반환
        if (nseqs.size() > 1) {
            return badRequest("소식지를 하나만 선택해주세요");
        }

        // 소식지 정보 수정 페이지로 리다이렉트
        return ok("", "/notice/update?nseq=" + nseqs.get(0));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "nseqs", required = false) List<Integer> nseqs) {
        // nseqs 값이 없는 경우 요청 거부 결과 반환
        if (nseqs == null || nseqs.isEmpty()) {
            return badRequest("소식지를 선택해주세요");
        }

        // 소식지 정보 제거에 실패한 경우 실패 결과 반환
        if (noticeService.deleteNotices(nseqs) == 0) {
            return badRequest("소식지 정보 제거에 실패했습니다");
        }

        // 소식지 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("소식지 정보를 제거했습니다", ":reload");
    }

}
