package com.team4.artgallery.controller;

import com.team4.artgallery.aspect.annotation.CheckAdmin;
import com.team4.artgallery.controller.annotation.LoginMember;
import com.team4.artgallery.dto.MemberDto;
import com.team4.artgallery.dto.NoticeDto;
import com.team4.artgallery.dto.filter.NoticeFilter;
import com.team4.artgallery.service.NoticeService;
import com.team4.artgallery.service.ResponseService;
import com.team4.artgallery.util.Pagination;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @Delegate
    private final ResponseService responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute NoticeFilter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model
    ) {
        // 소식지 목록을 가져올 때 카테고리에 따라 다른 페이지로 리다이렉트
        switch (filter.getCategory()) {
            case "매거진":
                return "redirect:/notice/magazine";
            case "신문":
                return "redirect:/notice/newspaper";
        }

        // 검색 조건에 따라 소식지 목록을 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(noticeService.countNotices(filter))
                .setUrlTemplate("/notice?page=%d" + filter.toUrlParam());

        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pagination);
        model.addAttribute("noticeList", noticeService.getNotices(filter, pagination));
        return "notice/noticeList";
    }

    @GetMapping({"/{nseq}", "/view/{nseq}"})
    public String view(@PathVariable(value = "nseq") Integer nseq, HttpSession session, Model model) {
        // 소식지 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        if (noticeDto == null) {
            return "util/404";
        }

        // 소식지를 읽은 것으로 처리
        noticeService.markAsRead(session, nseq);

        // 소식지 정보를 뷰에 전달
        model.addAttribute("noticeDto", noticeDto);
        return "notice/noticeView";
    }

    @CheckAdmin
    @GetMapping("/update")
    public String update(@RequestParam(value = "nseq") Integer nseq, Model model) {
        // 소식지 정보를 가져올 수 없는 경우 404 페이지로 포워딩
        NoticeDto noticeDto = noticeService.getNotice(nseq);
        if (noticeDto == null) {
            return "util/404";
        }

        // 소식지 정보를 뷰에 전달
        model.addAttribute("noticeDto", noticeDto);
        return "notice/noticeForm";
    }

    @CheckAdmin
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Valid @ModelAttribute NoticeDto noticeDto,
            @LoginMember MemberDto loginMember
    ) {
        // 작성자를 로그인 정보로부터 가져와서 소식지 정보에 설정
        noticeDto.setAuthor(loginMember.getId());

        // 소식지 수정에 실패한 경우 500 에러 반환
        if (noticeService.updateNotice(noticeDto) == 0) {
            return internalServerError("소식지 작성에 실패했습니다.");
        }

        // 소식지 수정에 성공한 경우 200 성공 반환
        return ok("소식지 수정이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @GetMapping("/write")
    public String write() {
        return "notice/noticeForm";
    }

    @CheckAdmin
    @PostMapping("/write")
    public ResponseEntity<?> write(
            @Valid @ModelAttribute NoticeDto noticeDto,
            @LoginMember MemberDto loginMember
    ) {
        // 작성자를 로그인 정보로부터 가져와서 소식지 정보에 설정
        noticeDto.setAuthor(loginMember.getId());

        // 소식지 작성에 실패한 경우 500 에러 반환
        if (noticeService.createNotice(noticeDto) == 0) {
            return internalServerError("소식지 작성에 실패했습니다.");
        }

        // 소식지 작성에 성공한 경우 200 성공 반환
        return ok("소식지 작성이 완료되었습니다.", "/notice/" + noticeDto.getNseq());
    }

    @CheckAdmin
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "nseq") Integer nseq) {
        // 소식지 삭제 실패 시 오류 결과 반환
        if (noticeService.deleteNotice(nseq) != 1) {
            return badRequest("소식지 삭제에 실패했습니다.");
        }

        // 소식지 삭제 성공 시 성공 결과 반환
        return ok("소식지가 삭제되었습니다.", "/notice");
    }

    @GetMapping("/magazine")
    public String magazine() {
        return "notice/noticeMagazine";
    }

    @GetMapping("/newspaper")
    public String newspaper() {
        return "notice/noticeNewspaper";
    }

}
