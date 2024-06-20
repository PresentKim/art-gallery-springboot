package com.team4.artgallery.controller;

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

@Controller
@RequestMapping("/artwork")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArtworkController {

    private final ArtworkService artworkService;

    private final MemberService memberService;

    @Delegate
    private final ResponseHelper responseHelper;

    @GetMapping("")
    public String list(
            @ModelAttribute IArtworkDao.ArtworkFilter filter,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model
    ) {
        // 전시 여부는 무시합니다. 항상 전시 중인 작품만 보여줍니다.
        filter.setDisplayyn("Y");

        // 검색 조건이 있을 경우 검색 결과를, 없을 경우 전체 작품 목록을 가져옵니다.
        Pagination.Pair<ArtworkDto> pair = artworkService.getOrSearchArtworks(page, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("pagination", pair.pagination());
        model.addAttribute("artworkList", pair.list());
        return "artwork/artworkList";
    }

    @GetMapping("/view")
    public String view(@RequestParam(value = "aseq") int aseq, Model model) {
        model.addAttribute("artworkDto", artworkService.findArtwork(aseq));
        return "artwork/artworkView";
    }

    @GetMapping("/update")
    public String update() {
        return "artwork/artworkUpdateForm";
    }

    @PostMapping("/toggleArtworkDisplay")
    public ResponseEntity<?> toggleArtworkDisplay(@RequestParam(value = "aseq") int aseq, HttpSession session) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 전시 여부 변경 실패 시 오류 결과 반환
        if (artworkService.toggleArtworkDisplay(aseq) != 1) {
            return badRequest("전시 여부 변경에 실패했습니다.");
        }

        // 전시 여부 변경 성공 시 성공 결과 반환
        return ok("전시 여부가 변경되었습니다.");
    }

    @GetMapping("/write")
    public String write() {
        return "artwork/artworkWriteForm";
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(value = "aseq") int aseq, HttpSession session) {
        // 관리자가 아닌 경우 요청 거부 결과 반환
        if (!memberService.isAdmin(session)) {
            return forbidden();
        }

        // 작품 삭제 실패 시 오류 결과 반환
        if (artworkService.deleteArtwork(aseq) != 1) {
            return badRequest("작품 삭제에 실패했습니다.");
        }

        // 작품 삭제 성공 시 성공 결과 반환
        return ok("작품이 삭제되었습니다.", "/artwork");
    }

}
