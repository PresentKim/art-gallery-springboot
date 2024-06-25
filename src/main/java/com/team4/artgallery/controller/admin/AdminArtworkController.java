package com.team4.artgallery.controller.admin;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.service.ArtworkService;
import com.team4.artgallery.service.MemberService;
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
@RequestMapping("/admin/artwork")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdminArtworkController {

    private final MemberService memberService;

    private final ArtworkService artworkService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping({"", "/"})
    public String list(
            @ModelAttribute IArtworkDao.ArtworkFilter filter,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            HttpSession session,
            Model model
    ) {
        // 관리자가 아닌 경우 404 페이지로 포워딩
        if (!memberService.isAdmin(session)) {
            System.out.println("관리자가 아닙니다");
            return "util/404";
        }

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 예술품 목록을 가져옵니다.
        Pagination.Pair<ArtworkDto> pair = artworkService.getOrSearchArtworks(page, filter, "admin/artwork");
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("artworkList", pair.list());
        return "admin/adminArtworkList";
    }

    @PostMapping("/update")
    public ResponseEntity<?> edit(
            @RequestParam(value = "aseqs", required = false) List<Integer> aseqs,
            HttpSession session
    ) {
        // aseqs 값이 없는 경우 요청 거부 결과 반환
        if (aseqs == null || aseqs.isEmpty()) {
            return badRequest("예술품을 선택해주세요");
        }

        // aseqs 값이 두개 이상인 경우 요청 거부 결과 반환
        if (aseqs.size() > 1) {
            return badRequest("예술품을 하나만 선택해주세요");
        }

        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 예술품 정보 수정 페이지로 리다이렉트
        return ok("", "/artwork/update?aseq=" + aseqs.get(0));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "aseqs", required = false) List<Integer> aseqs,
            HttpSession session
    ) {
        // aseqs 값이 없는 경우 요청 거부 결과 반환
        if (aseqs == null || aseqs.isEmpty()) {
            return badRequest("예술품을 선택해주세요");
        }

        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 예술품 정보 제거에 실패한 경우 실패 결과 반환
        if (artworkService.deleteArtworks(aseqs) == 0) {
            return badRequest("예술품 정보 제거에 실패했습니다");
        }

        // 예술품 정보 제거에 성공한 경우 성공 결과 반환 (페이지 새로고침)
        // TODO: 새고로침 없이 HTML 요소를 변경하는 방법으로 수정
        return ok("예술품 정보를 제거했습니다", ":reload");
    }

}
